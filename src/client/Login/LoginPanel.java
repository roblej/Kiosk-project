package client.Login;

import client.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {

    JPanel southPanel,northPanel;
    public JButton inBtn,outBtn,adminBtn;
    MainFrame f;
    public LoginPanel(MainFrame f) {
        this.f = f; // MainFrame 인스턴스를 저장
        setLayout(new BorderLayout()); // 레이아웃 매니저 설정
        southPanel = new JPanel();
        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout(FlowLayout.LEFT));


        inBtn = new JButton("매장");
        outBtn = new JButton("포장");
        adminBtn = new JButton("관리자");
        adminBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = JOptionPane.showInputDialog(LoginPanel.this,"관리자 인증 번호를 입력하세요","관리자 로그인", javax.swing.JOptionPane.QUESTION_MESSAGE);
                if(!str.isEmpty()) {
                    // 관리자 인증 로직 추가
                    if(str.equals("1234")) {
                        JOptionPane.showMessageDialog(LoginPanel.this, "관리자 인증 성공", "인증 성공", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        f.cardLayout.show(f.cardPanel, "AdminCard"); // 관리자 페이지로 이동
                    }
                    else
                        JOptionPane.showMessageDialog(LoginPanel.this, "관리자 인증 실패", "인증 실패", JOptionPane.ERROR_MESSAGE);
                    // 관리자 페이지로 이동하는 로직 추가
                } else {
                    JOptionPane.showMessageDialog(LoginPanel.this, "인증 번호를 입력하세요", "오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        northPanel.add(adminBtn);
        add(northPanel, BorderLayout.NORTH); // 북쪽 패널을 추가

        southPanel.add(inBtn);
        southPanel.add(outBtn);
        add(southPanel, BorderLayout.SOUTH); // 남쪽 패널을 추가
        setBackground(Color.LIGHT_GRAY); // 배경색 설정
        JLabel label = new JLabel("로그인 패널", SwingConstants.CENTER);


    }
}
