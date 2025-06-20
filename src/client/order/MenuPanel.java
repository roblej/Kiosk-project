package client.order;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MenuPanel extends JPanel {

    /**
     * 기능이 없는 생성자입니다.
     * 정적인 메뉴 버튼들만 생성하여 화면에 표시합니다.
     */
    public MenuPanel() {
        setLayout(new GridLayout(0, 3, 15, 15));
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);

        // 고정된 메뉴 버튼들을 생성하여 추가합니다.
        add(new MenuButton("아메리카노", 4500));
        add(new MenuButton("카페라떼", 5000));
        add(new MenuButton("카푸치노", 5000));
        add(new MenuButton("자몽에이드", 6000));
        add(new MenuButton("레몬에이드", 6000));
        add(new MenuButton("치즈케이크", 7000));
    }
}