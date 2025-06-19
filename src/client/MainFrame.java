package client;

import Ex.CardPanel2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainFrame extends JFrame {

    public JPanel cardPanel; // CardLayout이 적용될 패널
    public CardLayout cardLayout; // CardLayout 매니저

    public MainFrame() {
        setTitle("CardLayout 팀 프로젝트 예시");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 800);
        setLocationRelativeTo(null); // 화면 중앙에 배치

        // CardLayout을 적용할 패널 생성
        cardLayout = new CardLayout();

        cardPanel = new JPanel(cardLayout);

        // 각 카드 패널 인스턴스 생성 및 CardLayout에 추가
        // 각 카드는 별도의 .java 파일(클래스)로 구현됩니다.
        LoginPanel loginPanel = new LoginPanel();
        CardPanel1 panel1 = new CardPanel1();
        cardPanel.add(loginPanel, "LoginPanel"); // "LoginPanel" 이름으로 추가
        cardPanel.add(panel1, "Panel1"); // "Panel1"이라는 이름으로 추가

        // 컨트롤 버튼 생성 (패널 전환용)
        JPanel controlPanel = new JPanel();
        JButton btn1 = new JButton("Panel 1로 이동");
        JButton btn2 = new JButton("Panel 2로 이동");
        loginPanel.btn_in.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //매장식사
                //LoginDialog 호출
                LoginDialog loginDialog = null;
                try {
                    loginDialog = new LoginDialog(MainFrame.this);
                    loginDialog.setVisible(true); // 대화상자 표시
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
//                cardLayout.show(cardPanel, "Panel1");
            }
        });
        loginPanel.btn_out.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //포장식사
                cardLayout.show(cardPanel, "Panel1");
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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}