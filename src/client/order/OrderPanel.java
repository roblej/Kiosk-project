package client.order;

import client.MainFrame;
import javax.swing.*;
import java.awt.*;

public class OrderPanel extends JPanel {

    public CategoryPanel categoryPanel;
    public MenuPanel menuPanel;
    public CartPanel cartPanel;

    public OrderPanel(MainFrame mainFrame) {
        setLayout(new BorderLayout());

        // CategoryPanel만 DB 연동을 위해 mainFrame 참조를 전달합니다.
        categoryPanel = new CategoryPanel(mainFrame);

        // MenuPanel과 CartPanel은 기능이 없는 UI 전용 버전을 생성합니다.
        menuPanel = new MenuPanel();
        cartPanel = new CartPanel();

        // 화면에 패널들을 배치합니다.
        add(categoryPanel, BorderLayout.NORTH);
        JScrollPane menuScrollPane = new JScrollPane(menuPanel);
        add(menuScrollPane, BorderLayout.CENTER);
        add(cartPanel, BorderLayout.SOUTH);
    }
}