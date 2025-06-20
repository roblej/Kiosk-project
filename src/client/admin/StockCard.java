package client.admin;

import client.MainFrame;
import com.sun.tools.javac.Main;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import vo.ProductsVO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.List;

public class StockCard extends JPanel {


    JCheckBox[] chk_ar;
    JPanel stockPanel;
    JLabel s_SearchLb;
    JButton s_SearchBtn;
    JTable stockTable;
    JScrollPane stockScroll;
    String[][] data;
    String[] s_name = {"상품명", "현재수량"};
    SqlSessionFactory factory;
    List<ProductsVO> products;
    ArrayList<String> cat_list;
    MainFrame f;


    public StockCard(MainFrame f){
        this.f = f;
        this.cat_list = new ArrayList<>();
        initComponents();//화면구성

        init();//DB연결

        allData();

        //이벤트감지자 등록
        s_SearchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Map<String, ArrayList<String>> map = new HashMap<>();
                map.put("cat_list", cat_list);

                SqlSession ss = factory.openSession();
                List<ProductsVO> list = ss.selectList("products.search_cat", map);
                viewTable(list);
                ss.close();
            }
        });
    }//생성자의 끝

    private void initComponents(){

        stockPanel = new JPanel();
        s_SearchLb = new JLabel();
        s_SearchLb.setText("검색");
        stockTable = new JTable();
        s_SearchBtn= new JButton();

        ImageIcon icon = new ImageIcon("images/search.png");
        Image img = icon.getImage().getScaledInstance(
                40,40,Image.SCALE_SMOOTH);
        s_SearchBtn.setIcon(new ImageIcon(img));
        s_SearchBtn.setPreferredSize(new Dimension(50, 50));
        //jButton1.setBorder(new BevelBorder(BevelBorder.RAISED));
        s_SearchBtn.setBorder(BorderFactory.createLineBorder(Color.gray, 2));

        stockPanel.add(s_SearchBtn);
        stockPanel.add(s_SearchLb);


        stockTable.setModel(new DefaultTableModel(data, s_name));
        stockScroll = new JScrollPane(stockTable);
        this.setLayout(new BorderLayout());
        this.add(stockPanel, BorderLayout.NORTH);
        this.add(stockScroll, BorderLayout.CENTER);

    }

    private void allData(){
        SqlSession ss = factory.openSession();
        products = ss.selectList("all");

        //카테고리 목록 만들기 (중복 제거)
        Set<String> set = new HashSet<>();
        for (ProductsVO pvo : products) {
            set.add(pvo.getP_category());
        }

        cat_list = new ArrayList<>(set); //HashSet을 ArrayList로 변환

        viewCat(); //이제 cat_list 기반으로 체크박스 생성

        ss.close();
    }

    private void viewCat(){
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
        data = new String[products.size()][s_name.length];
        int i =0;
        for(ProductsVO vo : list){
            data[i][0] = vo.getP_name();//상품명
            data[i][1] = vo.getP_stock();//현재수량
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


}
