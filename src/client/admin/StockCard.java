package client.admin;

import client.MainFrame;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import vo.ProductsVO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.List;

public class StockCard extends JPanel {


    JCheckBox[] chk_ar;
    JPanel stockPanel;
    JPanel backPanel;
    JLabel s_SearchLb;
    JButton s_SearchBtn;
    JButton s_backBtn;
    JButton s_addBtn;
    JTable stockTable;
    JScrollPane stockScroll;
    String[][] data;
    String[] s_name = {"상품코드", "상품명", "가격", "재고"};
    SqlSessionFactory factory;
    List<ProductsVO> list;
    MainFrame f;
    int i;

    public StockCard(MainFrame f){
        this.f = f;

        initComponents();//화면구성

        init();//DB연결

        allData();

        //이벤트감지자 등록

        s_addBtn.addActionListener(new ActionListener() {//상품추가 버튼
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductsVO pvo = new ProductsVO();
                new AddDialog(f, true, pvo, StockCard.this);

            }
        });

        s_SearchBtn.addActionListener(new ActionListener() {//검색 버튼
            @Override
            public void actionPerformed(ActionEvent e) {
              ArrayList<String> cat_list = new ArrayList<>();
                cat_list.clear();

                for(JCheckBox box : chk_ar){
                    if(box.isSelected()){
                        String str = box.getText();
                        cat_list.add(str);

                    }
                }
                SqlSession ss = factory.openSession();


                if(cat_list != null && cat_list.isEmpty()){
                    //체크한 항목이 없다면 전체목록 조회
                    list = ss.selectList("products.all");
                }else {
                    Map<String, ArrayList<String>> map = new HashMap<>();
                    map.put("cat_list", cat_list);
                    list = ss.selectList("products.search_cat", map);
                }

                viewTable(list);
                ss.close();
            }
        });

        s_backBtn.addActionListener(new ActionListener() {//뒤로가기 버튼
            @Override
            public void actionPerformed(ActionEvent e) {
                f.cardLayout.show(f.cardPanel, "AdminCard");
            }
        });

        stockTable.addMouseListener(new MouseAdapter() {//테이블 더블클릭 했을때
            @Override
            public void mouseClicked(MouseEvent e) {//상품 더블클릭 이벤트
                int cnt = e.getClickCount();
                if(cnt == 2){
                    i = stockTable.getSelectedRow();
                    ProductsVO pvo = list.get(i);

                    new StockDialog(f,true, pvo, StockCard.this);
                }
            }
        });
    }//생성자의 끝

    private void initComponents(){

        stockPanel = new JPanel();
        backPanel = new JPanel();
        s_SearchLb = new JLabel();
        s_SearchLb.setText("검색");
        s_addBtn =  new JButton("상품추가");
        stockTable = new JTable();
        s_SearchBtn = new JButton();
        s_backBtn = new JButton();
        ImageIcon search_icon = new ImageIcon("images/search.png");
        ImageIcon back_icon = new ImageIcon("images/back.png");
        Image img = search_icon.getImage().getScaledInstance(
                40,40,Image.SCALE_SMOOTH);
        Image img2 = back_icon.getImage().getScaledInstance(
                40,40,Image.SCALE_SMOOTH);

        s_backBtn.setIcon(new ImageIcon(img2));
        s_backBtn.setPreferredSize(new Dimension(50, 50));
        s_backBtn.setBorder(BorderFactory.createLineBorder(Color.gray, 2));

        s_SearchBtn.setIcon(new ImageIcon(img));
        s_SearchBtn.setPreferredSize(new Dimension(50, 50));
        s_SearchBtn.setBorder(BorderFactory.createLineBorder(Color.gray, 2));

        backPanel.add(s_backBtn);
        stockPanel.add(s_addBtn);
        stockPanel.add(s_SearchBtn);
        stockPanel.add(s_SearchLb);

        stockTable.setModel(new DefaultTableModel(data, s_name));
        stockScroll = new JScrollPane(stockTable);
        this.setLayout(new BorderLayout());
        this.add(stockPanel, BorderLayout.NORTH);
        this.add(backPanel, BorderLayout.SOUTH);
        this.add(stockScroll, BorderLayout.CENTER);

    }

    private void allData(){
        SqlSession ss = factory.openSession();
        list = ss.selectList("products.all");

        //카테고리 목록 만들기 (중복 제거)
        Set<String> set = new HashSet<>();
        for (ProductsVO pvo : list) {
            set.add(pvo.getP_category());
        }

        ArrayList<String> cat_list = new ArrayList<>(set); //HashSet을 ArrayList로 변환

        viewCat(cat_list); //이제 cat_list 기반으로 체크박스 생성

        viewTable(list);

        ss.close();
    }

    private void viewCat( ArrayList<String> cat_list){
        chk_ar = new JCheckBox[cat_list.size()];
        int i = 0;
        for (String ca : cat_list) {
            chk_ar[i] = new JCheckBox(ca);
            stockPanel.add(chk_ar[i]);
            i++;
        }

        stockPanel.revalidate();
        stockPanel.repaint();

    }
    private void viewTable(List<ProductsVO> list){
        data = new String[list.size()][s_name.length];
        int i =0;
        for(ProductsVO vo : list){
            data[i][0] = vo.getP_code();//상품코드
            data[i][1] = vo.getP_name();//상품이름
            data[i][2] = vo.getP_price();//상품가격
            data[i][3] = vo.getP_stock();//재고
            i++;

        }
        stockTable.setModel(new DefaultTableModel(data, s_name){
            @Override
            public  boolean isCellEditable(int row, int column){
                return false;
            }
        });
    }

    private void init(){
        try {
            Reader r = Resources.getResourceAsReader("config/conf.xml");
            factory = new SqlSessionFactoryBuilder().build(r);
            r.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateData(ProductsVO pvo){
        SqlSession ss = factory.openSession();
        int cnt = ss.update("products.edit", pvo);
        if(cnt > 0){
            ss.commit();
            stockTable.setValueAt(pvo.getP_price(), i, 2);
            stockTable.setValueAt(pvo.getP_stock(), i, 3);
            list.set(i,pvo);
        }else
            ss.rollback();
        ss.close();
    }

    public void deleteData(String code) {
        SqlSession ss = factory.openSession();
        int cnt = ss.delete("products.del", code);
        if (cnt > 0) {
            ss.commit();
            ArrayList<String> cat_list = new ArrayList<>();
            for (JCheckBox box : chk_ar) {
                if (box.isSelected()) {
                    String str = box.getText();
                    cat_list.add(str);
                }
            }
            if (cat_list != null && cat_list.isEmpty()) {
                //체크한 항목이 없다면 전체목록 조회
                list = ss.selectList("products.all");
            } else {
                Map<String, ArrayList<String>> map = new HashMap<>();
                map.put("cat_list", cat_list);
                list = ss.selectList("products.search_cat", map);
            }
            viewTable(list);
            ss.close();
        }
    }
    public void addData(ProductsVO pvo){
        if(pvo != null ){
            Map<String, String> map = new HashMap<>();
            map.put("p_code", pvo.getP_code());
            map.put("p_name", pvo.getP_name());
            map.put("p_price", pvo.getP_price());
            map.put("p_size", pvo.getP_size());
            map.put("p_options", pvo.getP_options());
            map.put("p_category", pvo.getP_category());
            map.put("p_image_url", pvo.getP_image_url());
            map.put("p_stock", pvo.getP_stock());

            SqlSession ss = factory.openSession();
            int cnt = ss.insert("products.add", pvo);
            if(cnt > 0){
                ss.commit();
                ArrayList<String> cat_list = new ArrayList<>();
                for (JCheckBox box : chk_ar) {
                    if (box.isSelected()) {
                        String str = box.getText();
                        cat_list.add(str);
                    }
                }
                if (cat_list != null && cat_list.isEmpty()) {
                    //체크한 항목이 없다면 전체목록 조회
                    list = ss.selectList("products.all");
                } else {
                    Map<String, ArrayList<String>> map2 = new HashMap<>();
                    map2.put("cat_list", cat_list);
                    list = ss.selectList("products.search_cat", map2);
                }
                viewTable(list);
                ss.close();

            }
        }

    }

}
