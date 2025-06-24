package client.order;

import vo.ProductsVO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
// import java.awt.image.BufferedImage; // BufferedImage는 더 이상 필요 없으므로 주석 처리하거나 삭제합니다.

public class MenuButton extends JPanel {

    public MenuButton(ProductsVO vo) {
        setBackground(Color.WHITE);
        setBorder(new LineBorder(new Color(220, 220, 220)));
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
        JLabel imageLabel;
        try {
            // 1. DB에서 "images/파일명.png" 형식의 순수한 리소스 경로를 가져옴
            String imagePath = vo.getP_image_url();

            // 2. 맨 앞에 "/"를 붙여 클래스패스 절대경로로 만듦
            java.net.URL imageUrl = MenuButton.class.getResource("/" + imagePath);

            if (imageUrl == null) {
                throw new Exception("리소스를 찾을 수 없음: /" + imagePath);
            }

            // 3. URL로 ImageIcon 생성 및 크기 조절
            ImageIcon icon = new ImageIcon(imageUrl);
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            ImageIcon finalIcon = new ImageIcon(scaledImg);
            imageLabel = new JLabel(finalIcon);

        } catch (Exception e) {
            System.err.println("이미지 로드 실패: " + vo.getP_image_url() + " | " + e.getMessage());
            imageLabel = new JLabel("이미지 없음");
            imageLabel.setPreferredSize(new Dimension(100, 100));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        // --- 여기까지 수정 ---

        // --- 텍스트 컴포넌트 (기존 코드 유지) ---
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

        textPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JPanel imageWrapper = new JPanel();
        imageWrapper.setOpaque(false);
        imageWrapper.add(imageLabel);

        // --- 최종 배치 (기존 코드 유지) ---
        add(imageWrapper, BorderLayout.CENTER);
        add(textPanel, BorderLayout.SOUTH);
    }
}