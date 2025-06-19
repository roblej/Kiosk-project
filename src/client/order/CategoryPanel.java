package client.order; // 패키지 변경

import javax.swing.*;
import java.awt.*;

public class CategoryPanel extends JPanel {

    private OrderPanel orderPanel; // MainFrame 대신 OrderPanel을 참조

    // 생성자에서 MainFrame 대신 OrderPanel을 받도록 수정
    public CategoryPanel(OrderPanel orderPanel) {
        this.orderPanel = orderPanel;
        setBackground(Color.LIGHT_GRAY);

        // 임시 버튼들
        add(new JButton("모든 메뉴"));
        add(new JButton("추천 메뉴"));
        add(new JButton("커피"));
        add(new JButton("디저트"));
    }
}