package client.order; // 패키지 변경

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    private OrderPanel orderPanel; // MainFrame 대신 OrderPanel을 참조

    // 생성자에서 MainFrame 대신 OrderPanel을 받도록 수정
    public MenuPanel(OrderPanel orderPanel) {
        this.orderPanel = orderPanel;
        setBackground(Color.WHITE);
        // 한 줄에 3개씩 표시되도록 GridLayout으로 설정
        setLayout(new GridLayout(0, 3, 15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // 임시 메뉴 버튼들 (나중에 DB 연동 후 동적으로 생성)
        add(new JButton("메뉴1"));
        add(new JButton("메뉴2"));
        add(new JButton("메뉴3"));
        add(new JButton("메뉴4"));
        add(new JButton("메뉴5"));
    }
}