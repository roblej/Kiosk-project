package client.order;

import vo.ProductsVO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuButton extends JPanel {

    public MenuButton(ProductsVO vo) {
        setBackground(Color.WHITE);
        setBorder(new LineBorder(new Color(220, 220, 220)));
        // 1. 스크롤바가 생기지 않도록 날씬한 크기를 다시 적용
        setPreferredSize(new Dimension(130, 170));
        setLayout(new BorderLayout(0, 5));

        String name = vo.getP_name();
        String priceText = "";
        try {
            int price = Integer.parseInt(vo.getP_price());
            priceText = String.format("%,d원", price);
        } catch (NumberFormatException e) {
            System.err.println("가격 파싱 오류: " + vo.getP_price());
            priceText = "가격 정보 오류";
        }


        // --- 이미지 컴포넌트 ---
        ImageIcon placeholderIcon = new ImageIcon(new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB));
        JLabel imageLabel = new JLabel(placeholderIcon);

        // --- 텍스트 컴포넌트 ---
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel priceLabel = new JLabel(priceText);
        priceLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
        priceLabel.setForeground(new Color(80, 80, 80));
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.add(nameLabel);
        textPanel.add(priceLabel);

        // 3. 텍스트를 위로 올리기 위한 하단 여백
        textPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // 이미지 라벨을 래퍼 패널로 감싸기
        JPanel imageWrapper = new JPanel();
        imageWrapper.setOpaque(false);
        imageWrapper.add(imageLabel);

        // --- 최종 배치 ---
        add(imageWrapper, BorderLayout.CENTER);
        add(textPanel, BorderLayout.SOUTH);
    }
}