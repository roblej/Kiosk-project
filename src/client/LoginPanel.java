package client;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    JPanel south_p,center_p;
    JButton btn_in,btn_out;


    public LoginPanel() {
        setLayout(new BorderLayout()); // 레이아웃 매니저 설정
        south_p = new JPanel();
        south_p.setLayout(new FlowLayout(FlowLayout.CENTER));

        btn_in = new JButton("매장");
        btn_out = new JButton("포장");



        south_p.add(btn_in);
        south_p.add(btn_out);
        add(south_p, BorderLayout.SOUTH); // 남쪽 패널을 추가
        setBackground(Color.LIGHT_GRAY); // 배경색 설정
        JLabel label = new JLabel("로그인 패널", SwingConstants.CENTER);


    }
}
