package client.admin;
import client.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminCard extends JPanel {

    MainFrame f;
    UserManagerPanel UMP;


    public AdminCard(MainFrame f, UserManagerPanel ump) {
        this.f = f;
        this.UMP = ump;
//        this.factory = factory;

        setLayout(new GridLayout(4, 2, 10, 10));
        //버튼생성
        JButton openBtn = new JButton("오픈");
        JButton setBtn = new JButton("마감/정산");
        JButton payBtn = new JButton("판매분석");
        JButton couponBtn = new JButton("쿠폰사용내역");
        JButton productBtn = new JButton("상품정보관리");
        JButton customBtn = new JButton("회원관리");
        JButton orderBtn = new JButton("주문처리");
        JButton closeBtn = new JButton("종료");
        JButton[] buttons = {openBtn, setBtn, payBtn, couponBtn, productBtn, customBtn, orderBtn, closeBtn};
        // 2. 반복문으로 배열을 순회하며 배경색을 흰색으로 설정합니다.
        for (JButton btn : buttons) {
            btn.setBackground(Color.WHITE);
        }

        //화면에 버튼 삽입
        add(openBtn);
        add(setBtn);
        add(payBtn);
        add(couponBtn);
        add(productBtn);
        add(customBtn);
        add(orderBtn);
        add(closeBtn);

        //버튼 글씨체, 폰트, 사이즈 수정
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
               f.cardLayout.show(f.cardPanel, "LoginPanel");
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
                f.cardLayout.show(f.cardPanel, "AnalyzePanel");
            }
        });

        couponBtn.addActionListener(new ActionListener() {//쿠폰사용내역
            @Override
            public void actionPerformed(ActionEvent e) {
                f.cardLayout.show(f.cardPanel, "CouponManagerPanel");
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
                UMP.viewAll();
                f.cardLayout.show(f.cardPanel, "userManagerPanel");
            }
        });

        orderBtn.addActionListener(new ActionListener() {//주문처리
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrderManager(f);
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