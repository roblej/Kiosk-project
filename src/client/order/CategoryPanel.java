package client.order; // 패키지 변경

import client.MainFrame;

import javax.swing.*;
import java.awt.*;

/*
카테고리에 나오는 음료의 종류도 VO에서 받아와야 한다.
 */

public class CategoryPanel extends JPanel {

    private OrderPanel orderPanel; // MainFrame 대신 OrderPanel을 참조

    // 생성자에서 MainFrame 대신 OrderPanel을 받도록 수정
    public CategoryPanel(OrderPanel orderPanel, MainFrame f) {
        this.orderPanel = orderPanel;
        setBackground(Color.LIGHT_GRAY);

        // 임시 버튼들
        add(new JButton("모든 메뉴"));
        add(new JButton("추천 메뉴"));
        add(new JButton("커피"));
        add(new JButton("디저트"));
    }
}