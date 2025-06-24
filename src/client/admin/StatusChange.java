package client.admin;

import client.MainFrame;
import org.apache.ibatis.session.SqlSession;
import vo.order_VO;
import vo.order_items_VO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/*
창 생성 시 조리중, 처리완료, 취소 위 영역에 주문한 메뉴들의 정보가 나와야 함
 */

public class StatusChange extends JDialog {

    JButton pending, process, finish;
    MyDialog parent;
    order_VO vo;
    JTable table;
    JScrollPane scrollPane;

    List<order_items_VO> list;
    MainFrame f;
    String str;

    String[] statusTable = {"주문번호", "주문상품", "주문수량", "주문가격", "사이즈", "옵션"};
    String[][] data;

    public StatusChange(MainFrame f, MyDialog parent, boolean modal, order_VO vo, String str) {
        super(parent, modal);
        this.f = f;
        this.parent = parent;
        this.vo = vo; // 저장
        this.str = str;

        list = getData(); // 값 채우기
        System.out.println(this.str);

        table = new JTable(new DefaultTableModel(data, statusTable) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        initComponents();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // 이벤트 감지자 등록
        pending.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vo.setO_status("조리중");
                parent.updateData(vo);
                dispose();
            }
        });

        process.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vo.setO_status("처리완료");
                parent.updateData(vo);
                dispose();
            }
        });

        finish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vo.setO_status("취소");
                parent.updateData(vo);
                dispose();
            }
        });
        this.setVisible(true);
    }

    private void initComponents(){
        JPanel center_p;
        JPanel south_p;
        center_p = new JPanel();
        south_p = new JPanel();
        scrollPane = new JScrollPane(table);
        center_p.add(scrollPane);
        south_p.add(pending = new JButton("조리중"));
        south_p.add(process = new JButton("처리완료"));
        south_p.add(finish = new JButton("취소"));
        this.add(center_p, BorderLayout.CENTER);
        this.add(south_p, BorderLayout.SOUTH);

//        table.setBackground(Color.WHITE);           // 배경 흰색
//        table.setShowGrid(false);                   // 선 없음
//        table.setIntercellSpacing(new Dimension(0, 0)); // 셀 간격 제거

        // 창 설정
        this.setSize(500, 300);
        this.setLocationRelativeTo(null); // 화면 가운데

    }

    public java.util.List<order_items_VO> getData(){
        SqlSession ss = f.factory.openSession();
        List<order_items_VO> list = ss.selectList("order_items.status", str);
        data = new String[list.size()][statusTable.length];
        int i = 0;
        for (order_items_VO vo : list) {
            // oi_idx, oi_id, product_code, oi_quantity, oi_price, oi_size, options
            // "주문번호", "주문상품", "주문수량", "주문가격", "사이즈", "옵션"
            data[i][0] = vo.getOi_id();            // 주문번호
            data[i][1] = vo.getProduct_code();   // 주문상품
            data[i][2] = vo.getOi_quantity();         // 주문수량
            data[i][3] = vo.getOi_price();          // 주문가격
            data[i][4] = vo.getOi_size();           // 사이즈
            data[i][5] = vo.getOptions();           // 옵션
            i++;
        }
        ss.close();
        return list;
    }

}
