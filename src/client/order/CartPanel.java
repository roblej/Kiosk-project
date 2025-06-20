package client.order; // 패키지 변경

import javax.swing.*;
import java.awt.*;
import javax.swing.border.MatteBorder;

public class CartPanel extends JPanel {

    private OrderPanel orderPanel; // MainFrame 대신 OrderPanel을 참조

    // 생성자에서 MainFrame 대신 OrderPanel을 받도록 수정
    public CartPanel(OrderPanel orderPanel) {
        this.orderPanel = orderPanel;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500, 220));
        setBackground(Color.WHITE);
        setBorder(new MatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));

        // (이하 장바구니 UI 구성 요소... - 이전 코드와 유사)
        add(new JLabel("장바구니", SwingConstants.CENTER), BorderLayout.NORTH);
        add(new JScrollPane(new JTextArea("장바구니 내용이 여기에 표시됩니다.")), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(new JLabel(" 총 금액: 0원"), BorderLayout.CENTER);
        bottomPanel.add(new JButton("결제하기"), BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
    }
}