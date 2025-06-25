package client;

import client.admin.ClosingSalesPanel;
import client.admin.*;
import client.Login.LoginDialog;
import client.Login.LoginPanel;
import client.order.*;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.Reader;

public class MainFrame extends JFrame {

    public JPanel cardPanel; // CardLayout이 적용될 패널
    public CardLayout cardLayout; // CardLayout 매니저
    public SqlSessionFactory factory;
    public static String userId; // 로그인한 사용자 ID를 저장할 변수
    public static int orderType; // 주문 타입을 저장할 변수 (0: 포장 1: 매장)
    public MainFrame() {
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
        OrderPanel orderPanel = new OrderPanel(this);
        CouponManagerPanel couponManagerPanel = new CouponManagerPanel(this);


        cardPanel.add(loginPanel, "LoginPanel"); // "LoginPanel" 이름으로 추가
        cardPanel.add(orderPanel, "orderPanel");
        cardPanel.add(adminCard, "AdminCard");
        cardPanel.add(closingSalesPanel, "ClosingSalesPanel"); // "ClosingSalesPanel" 이름으로 추가
        cardPanel.add(userManagerPanel, "userManagerPanel");
        cardPanel.add(couponManagerPanel, "CouponManagerPanel"); // "CouponManagerPanel" 이름으로 추가
        cardPanel.add(stockCard,"StockCard");

        this.add(cardPanel, BorderLayout.CENTER);

        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        // MainFrame 인스턴스를 생성하여 실행
        new MainFrame();
    }
}