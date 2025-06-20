package client.order;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class CartPanel extends JPanel {

    /**
     * 기능이 없는 생성자입니다.
     * 정적인 장바구니 항목과 UI 요소들만 표시합니다.
     */
    public CartPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500, 220));
        setBorder(BorderFactory.createTitledBorder("장바구니"));

        JPanel itemListPanel = new JPanel();
        itemListPanel.setLayout(new BoxLayout(itemListPanel, BoxLayout.Y_AXIS));
        itemListPanel.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(itemListPanel);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel totalPriceLabel = new JLabel("총 주문금액: 16,500원");
        totalPriceLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));

        JButton paymentButton = new JButton("결제하기");
        paymentButton.setFont(new Font("맑은 고딕", Font.BOLD, 18));

        bottomPanel.add(totalPriceLabel, BorderLayout.CENTER);
        bottomPanel.add(paymentButton, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

    }

    private void addStaticCartItem(JPanel targetPanel, String name, String price) {
        JPanel itemPanel = new JPanel(new BorderLayout(10, 0));
        itemPanel.setBackground(Color.WHITE);
        itemPanel.setBorder(new EmptyBorder(5, 5, 5, 10));

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));

        JLabel priceLabel = new JLabel(price);
        priceLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));

        JButton deleteButton = new JButton("X");
        deleteButton.setMargin(new Insets(2, 4, 2, 4));

        JPanel westPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        westPanel.setOpaque(false);
        westPanel.add(deleteButton);
        westPanel.add(nameLabel);

        itemPanel.add(westPanel, BorderLayout.CENTER);
        itemPanel.add(priceLabel, BorderLayout.EAST);

        targetPanel.add(itemPanel);
    }
}