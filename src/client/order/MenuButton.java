package client.order;

import vo.ProductsVO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
// import java.awt.event.MouseAdapter; // 이전 방식의 MouseAdapter는 더 이상 필요 없으므로 삭제합니다.

public class MenuButton extends JPanel {

    // 품절 상태를 저장하기 위한 멤버 변수 추가
    private boolean isSoldOut = false;

    public MenuButton(ProductsVO vo) {
        // --- 기본 패널 설정 ---
        setBorder(new LineBorder(new Color(220, 220, 220)));
        setPreferredSize(new Dimension(130, 170));
        setLayout(new BorderLayout());

        // --- 컴포넌트 생성 로직 (이전과 동일) ---
        String name = vo.getP_name();
        String priceText = "";
        try {
            int price = Integer.parseInt(vo.getP_price());
            priceText = String.format("%,d원", price);
        } catch (NumberFormatException e) {
            priceText = "가격 정보 오류";
        }

        JLabel imageLabel;
        ImageIcon originalIcon = null;
        try {
            String imagePath = vo.getP_image_url();
            java.net.URL imageUrl = MenuButton.class.getResource("/" + imagePath);
            if (imageUrl == null) {
                throw new Exception("리소스를 찾을 수 없음: /" + imagePath);
            }
            originalIcon = new ImageIcon(imageUrl);
            Image img = originalIcon.getImage();
            Image scaledImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imageLabel = new JLabel(new ImageIcon(scaledImg));
        } catch (Exception e) {
            imageLabel = new JLabel();
            imageLabel.setPreferredSize(new Dimension(100, 100));
        }

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

        JPanel contentPanel = new JPanel(new BorderLayout(0, 5));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBounds(0, 0, 130, 170);
        contentPanel.add(imageWrapper, BorderLayout.CENTER);
        contentPanel.add(textPanel, BorderLayout.SOUTH);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.add(contentPanel, JLayeredPane.DEFAULT_LAYER);

        this.add(layeredPane, BorderLayout.CENTER);

        // --- 재고 < 1 일 경우, 품절 상태로 UI 변경 ---
        try {
            if (Integer.parseInt(vo.getP_stock()) < 1) {
                this.isSoldOut = true; // 멤버 변수에 품절 상태 저장
            }
        } catch (NumberFormatException | NullPointerException e) {
            System.err.println("재고 정보 파싱 오류. p_stock 값: " + vo.getP_stock());
        }

        if (this.isSoldOut) {
            if (originalIcon != null) {
                Image grayImage = GrayFilter.createDisabledImage(originalIcon.getImage());
                Image scaledGrayImage = grayImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledGrayImage));
            }

            nameLabel.setForeground(Color.GRAY);
            priceLabel.setForeground(Color.GRAY);

            JLabel soldOutLabel = new JLabel("SOLD OUT");
            soldOutLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
            soldOutLabel.setForeground(Color.RED);
            soldOutLabel.setHorizontalAlignment(SwingConstants.CENTER);
            soldOutLabel.setBounds(0, 0, 130, 170);
            layeredPane.add(soldOutLabel, JLayeredPane.MODAL_LAYER);

            // 4. 클릭 불가하게 변경 (이전의 MouseAdapter 방식은 삭제)
            // 아래의 processMouseEvent 메소드가 이 역할을 대신합니다.
        }
    }

    /**
     * 마우스 이벤트를 처리하는 핵심 메소드.
     * 품절(isSoldOut=true) 상태일 경우, 모든 마우스 이벤트를 무시하고
     * 상위 클래스의 이벤트 처리(super.processMouseEvent)를 호출하지 않아
     * 등록된 다른 MouseListener들이 전혀 동작하지 않도록 합니다.
     */
    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (isSoldOut) {
            return; // 품절이면 아무것도 하지 않고 이벤트를 종료
        }
        // 품절이 아니면, 정상적으로 부모 클래스의 이벤트 처리 로직을 실행
        super.processMouseEvent(e);
    }
}