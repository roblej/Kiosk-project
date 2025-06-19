package client.admin;
import client.MainFrame;
import org.apache.ibatis.session.SqlSession;
import vo.order_VO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminCard extends JPanel {

    MyDialog dialog;
    MainFrame f;

//    SqlSessionFactory factory;

    public AdminCard(MainFrame f) {
        this.f = f;
//        this.factory = factory;

        setLayout(new GridLayout(4, 2, 10, 10));

        JButton bt1 = new JButton("오픈");
        JButton bt2 = new JButton("마감/정산");
        JButton bt3 = new JButton("결제관리");
        JButton bt4 = new JButton("쿠폰사용내역");
        JButton bt5 = new JButton("상품정보관리");
        JButton bt6 = new JButton("회원관리");
        JButton bt7 = new JButton("주문처리");
        JButton bt8 = new JButton("종료");

        add(bt1);
        add(bt2);
        add(bt3);
        add(bt4);
        add(bt5);
        add(bt6);
        add(bt7);
        add(bt8);

        bt1.setFont(new Font("맑은고딕", Font.BOLD, 20));
        bt2.setFont(new Font("맑은고딕", Font.BOLD, 20));
        bt3.setFont(new Font("맑은고딕", Font.BOLD, 20));
        bt4.setFont(new Font("맑은고딕", Font.BOLD, 20));
        bt5.setFont(new Font("맑은고딕", Font.BOLD, 20));
        bt6.setFont(new Font("맑은고딕", Font.BOLD, 20));
        bt7.setFont(new Font("맑은고딕", Font.BOLD, 20));
        bt8.setFont(new Font("맑은고딕", Font.BOLD, 20));


        //이벤트 감지자 등록
        bt1.addActionListener(new ActionListener() {//오픈버튼
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "오픈시작");
                //  int result = JOptionPane.showConfirmDialog(null, )
            }
        });
        bt2.addActionListener(new ActionListener() {//마감/정산
            @Override
            public void actionPerformed(ActionEvent e) {
                f.cardLayout.show(f.cardPanel, "ClosingSalesPanel");
            }
        });

        bt3.addActionListener(new ActionListener() {//결제관리
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        bt4.addActionListener(new ActionListener() {//쿠폰사용내역
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        bt5.addActionListener(new ActionListener() {//상품정보관리
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        bt6.addActionListener(new ActionListener() {//회원관리
            @Override
            public void actionPerformed(ActionEvent e) {
                f.cardLayout.show(f.cardPanel, "userManagerPanel");
            }
        });

        bt7.addActionListener(new ActionListener() {//주문처리
            @Override
            public void actionPerformed(ActionEvent e) {
                List<order_VO> list = getData();
                new MyDialog(f, true, data, o_name, f.factory, list);
            }
        });

        bt8.addActionListener(new ActionListener() {//종료
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);//종료
            }
        });

    }

    String[][] data;
    String[] o_name = {"주문번호", "결제금액", "주문상태", "고객ID"};
    public List<order_VO> getData(){
        SqlSession ss = f.factory.openSession();
        List<order_VO> list = ss.selectList("orders.status");
        data = new String[list.size()][o_name.length];
        int i = 0;
        for (order_VO vo : list) {
            data[i][0] = vo.getO_idx();            // 주문번호
            data[i][1] = vo.getO_total_amount();   // 결제금액
            data[i][2] = vo.getO_status();         // 주문상태
            data[i][3] = vo.getUser_id();          // 고객ID
            i++;
        }
        ss.close();
        return list;
    }

}