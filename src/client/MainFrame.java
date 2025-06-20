package client;

import client.Closing_sales.ClosingSalesPanel;
import client.admin.AdminCard;
import client.admin.CouponManagerPanel;
import client.admin.UserManagerPanel;
import client.Login.CardPanel1;
import client.Login.LoginDialog;
import client.Login.LoginPanel;
import client.admin.StockCard;
import client.order.OrderPanel;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Reader;

public class MainFrame extends JFrame {

    public JPanel cardPanel; // CardLayout이 적용될 패널
    public CardLayout cardLayout; // CardLayout 매니저
    public SqlSessionFactory factory;

    public MainFrame()  {
        Reader r = null;
        try {
            r = Resources.getResourceAsReader("config/conf.xml");
            factory = new SqlSessionFactoryBuilder().build(r);
            r.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setTitle("CardLayout 팀 프로젝트 예시");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 800);
        setLocationRelativeTo(null); // 화면 중앙에 배치

        // CardLayout을 적용할 패널 생성
        cardLayout = new CardLayout();

        cardPanel = new JPanel(cardLayout);

        // 각 카드 패널 인스턴스 생성 및 CardLayout에 추가
        // 각 카드는 별도의 .java 파일(클래스)로 구현됩니다.
        LoginPanel loginPanel = new LoginPanel(this);
        UserManagerPanel userManagerPanel = new UserManagerPanel(this);
        AdminCard adminCard = new AdminCard(this);
        ClosingSalesPanel closingSalesPanel = new ClosingSalesPanel(this);
        StockCard stockCard = new StockCard(this);
        OrderPanel orderPanel = new OrderPanel();
        CouponManagerPanel couponManagerPanel = new CouponManagerPanel(this);
        CardPanel1 panel1 = new CardPanel1();
        cardPanel.add(couponManagerPanel, "CouponManagerPanel"); // "CouponManagerPanel" 이름으로 추가
        cardPanel.add(loginPanel, "LoginPanel"); // "LoginPanel" 이름으로 추가
        cardPanel.add(orderPanel,"orderPanel");
        cardPanel.add(panel1, "Panel1"); // "Panel1"이라는 이름으로 추가
        cardPanel.add(adminCard, "AdminCard");
        cardPanel.add(closingSalesPanel, "ClosingSalesPanel"); // "ClosingSalesPanel" 이름으로 추가
        cardPanel.add(userManagerPanel, "userManagerPanel");
        cardPanel.add(stockCard,"StockCard");
        // 컨트롤 버튼 생성 (패널 전환용)
        JPanel controlPanel = new JPanel();
        JButton btn1 = new JButton("Panel 1로 이동");
        JButton btn2 = new JButton("Panel 2로 이동");
        loginPanel.inBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //매장식사
                //LoginDialog 호출
                LoginDialog loginDialog = null;
                loginDialog = new LoginDialog(MainFrame.this);
                loginDialog.setVisible(true); // 대화상자 표시
                //                cardLayout.show(cardPanel, "Panel1");
            }
        });

        loginPanel.outBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //포장식사
                cardLayout.show(cardPanel, "orderPanel"); // "Panel1"을 "orderPanel"로 변경
            }
        });

        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Panel1"); // "Panel1" 이름의 카드 보이기
            }
        });

        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Panel2"); // "Panel2" 이름의 카드 보이기
            }
        });

        controlPanel.add(btn1);
        controlPanel.add(btn2);

        // 프레임에 패널들 추가
        add(cardPanel, BorderLayout.CENTER);
//        add(controlPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new MainFrame().setVisible(true);
//            }
//        });
        new MainFrame();
    }
}