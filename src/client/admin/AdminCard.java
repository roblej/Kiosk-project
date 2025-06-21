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


    public AdminCard(MainFrame f) {
        this.f = f;
//        this.factory = factory;

        setLayout(new GridLayout(4, 2, 10, 10));

        JButton openBtn = new JButton("오픈");
        JButton setBtn = new JButton("마감/정산");
        JButton payBtn = new JButton("결제관리");
        JButton couponBtn = new JButton("쿠폰사용내역");
        JButton productBtn = new JButton("상품정보관리");
        JButton customBtn = new JButton("회원관리");
        JButton orderBtn = new JButton("주문처리");
        JButton closeBtn = new JButton("종료");

        add(openBtn);
        add(setBtn);
        add(payBtn);
        add(couponBtn);
        add(productBtn);
        add(customBtn);
        add(orderBtn);
        add(closeBtn);

        openBtn.setFont(new Font("맑은고딕", Font.BOLD, 20));
        setBtn.setFont(new Font("맑은고딕", Font.BOLD, 20));
        payBtn.setFont(new Font("맑은고딕", Font.BOLD, 20));
        couponBtn.setFont(new Font("맑은고딕", Font.BOLD, 20));
        productBtn.setFont(new Font("맑은고딕", Font.BOLD, 20));
        customBtn.setFont(new Font("맑은고딕", Font.BOLD, 20));
        orderBtn.setFont(new Font("맑은고딕", Font.BOLD, 20));
        closeBtn.setFont(new Font("맑은고딕", Font.BOLD, 20));


        //이벤트 감지자 등록
        openBtn.addActionListener(new ActionListener() {//오픈버튼
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "오픈시작");
                //  int result = JOptionPane.showConfirmDialog(null, )
            }
        });
        setBtn.addActionListener(new ActionListener() {//마감/정산
            @Override
            public void actionPerformed(ActionEvent e) {
                f.cardLayout.show(f.cardPanel, "ClosingSalesPanel");
            }
        });

        payBtn.addActionListener(new ActionListener() {//결제관리
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        couponBtn.addActionListener(new ActionListener() {//쿠폰사용내역
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        productBtn.addActionListener(new ActionListener() {//상품정보관리
            @Override
            public void actionPerformed(ActionEvent e) {
                f.cardLayout.show(f.cardPanel, "StockCard");

            }
        });

        customBtn.addActionListener(new ActionListener() {//회원관리
            @Override
            public void actionPerformed(ActionEvent e) {
                f.cardLayout.show(f.cardPanel, "userManagerPanel");
            }
        });

        orderBtn.addActionListener(new ActionListener() {//주문처리
            @Override
            public void actionPerformed(ActionEvent e) {
                new MyDialog(f, true);
            }
        });

        closeBtn.addActionListener(new ActionListener() {//종료
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);//종료
            }
        });

    }





}