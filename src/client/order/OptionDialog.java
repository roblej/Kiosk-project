package client.order;

import client.MainFrame;
import vo.ProductsVO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OptionDialog extends JDialog {

    int totalPrice = 0;
    int count = 1;

    ProductsVO product;
    JButton plusBtn, minusBtn;
    JButton okBtn, cancelBtn;

    JButton shortBtn, tallBtn, ventiBtn;
    private String selectedSize="";
    private int sizePriceModifier = 0;

    JPanel menuCenterPanel, countPanel;
    JPanel northPanel, centerPanel, southPanel;
    JLabel imgLabel, countLabel, priceLabel;
    JLabel menuLabel;

    OrderPanel p;
    MainFrame f;

    int defaultPrice;

    public OptionDialog(OrderPanel p, MainFrame f, ProductsVO product) {
        this.p = p;
        this.f = f;
        this.product = product;
        this.defaultPrice = Integer.parseInt(product.getP_price());

        resetValue();
        initcomponents();
        calPrice();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // '담기' 버튼 클릭 시, OrderPanel의 수정된 addToCart 메소드를 호출합니다.
        // 네 번째 인자로 현재 선택된 사이즈(selectedSize)를 전달합니다.
        okBtn.addActionListener(e -> {
            p.addToCart(product, count, totalPrice, selectedSize);
            dispose();
        });

        cancelBtn.addActionListener(e -> dispose());

        plusBtn.addActionListener(e -> {
            count++;
            updateCountAndPrice();
        });

        minusBtn.addActionListener(e -> {
            if (count > 1) {
                count--;
                updateCountAndPrice();
            }
        });

        shortBtn.addActionListener(e -> handleSizeSelection("Short", -500));
        tallBtn.addActionListener(e -> handleSizeSelection("Tall", 0));
        ventiBtn.addActionListener(e -> handleSizeSelection("Venti", 500));
    }

    public void initcomponents() {
        // ================= 상단 패널 =================
        northPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        northPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        try {
            String imagePath = product.getP_image_url();
            java.net.URL imageUrl = MenuButton.class.getResource("/" + imagePath);
            if (imageUrl == null) {
                throw new Exception("리소스를 찾을 수 없음: /" + imagePath);
            }
            ImageIcon icon = new ImageIcon(imageUrl);
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            ImageIcon finalIcon = new ImageIcon(scaledImg);
            imgLabel = new JLabel(finalIcon);
        } catch (Exception e) {
            System.err.println("이미지 로드 실패: " + product.getP_image_url() + " | " + e.getMessage());
            imgLabel = new JLabel("이미지 없음");
            imgLabel.setPreferredSize(new Dimension(120, 120));
            imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }

        JPanel imageWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        imageWrapper.add(imgLabel);
        northPanel.add(imageWrapper);

        menuCenterPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        menuLabel = new JLabel(product.getP_name(), SwingConstants.CENTER);
        menuLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        menuCenterPanel.add(menuLabel);

        countPanel = new JPanel(new GridLayout(1, 3, 5, 0));
        minusBtn = new JButton("-");
        minusBtn.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        countLabel = new JLabel("1", SwingConstants.CENTER);
        plusBtn = new JButton("+");
        plusBtn.setFont(new Font("맑은 고딕", Font.BOLD, 8));
        countLabel.setOpaque(true);
        countLabel.setBackground(Color.WHITE);
        countPanel.add(minusBtn);
        countPanel.add(countLabel);
        countPanel.add(plusBtn);
        menuCenterPanel.add(countPanel);
        northPanel.add(menuCenterPanel);

        priceLabel = new JLabel("", SwingConstants.CENTER);
        priceLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        northPanel.add(priceLabel);

        // ================= 중앙 패널 (옵션) =================
        centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel sizePanel = new JPanel(new GridLayout(1, 3, 10, 0));
        shortBtn = new JButton("Short (-500)");
        tallBtn = new JButton("Tall (기본)");
        ventiBtn = new JButton("Venti (+500)");
        sizePanel.add(shortBtn);
        sizePanel.add(tallBtn);
        sizePanel.add(ventiBtn);

        JPanel sizeWrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        sizeWrapperPanel.add(sizePanel);
        centerPanel.add(sizeWrapperPanel, BorderLayout.CENTER);

        updateSizeButtonUI();

        // ================= 하단 패널 (버튼) =================
        southPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        southPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        okBtn = new JButton("담기");
        cancelBtn = new JButton("취소");
        southPanel.add(okBtn);
        southPanel.add(cancelBtn);

        // ================= 프레임 조립 =================
        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        setSize(500, 350); // 다이얼로그 높이 조절
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateCountAndPrice() {
        countLabel.setText(String.valueOf(count));
        calPrice();
    }

    public void calPrice() {
        totalPrice = (defaultPrice + sizePriceModifier) * count;
        if (priceLabel != null) {
            priceLabel.setText(String.format("%,d원", totalPrice));
        }
    }

    private void handleSizeSelection(String sizeName, int priceModifier) {
        selectedSize = sizeName;
        sizePriceModifier = priceModifier;
        updateSizeButtonUI();
        calPrice();
    }

    private void updateSizeButtonUI() {
        shortBtn.setBackground(null);
        tallBtn.setBackground(null);
        ventiBtn.setBackground(null);

        Color selectedColor = new Color(180, 220, 255);
        switch (selectedSize) {
            case "Short":
                shortBtn.setBackground(selectedColor);
                break;
            case "Tall":
                tallBtn.setBackground(selectedColor);
                break;
            case "Venti":
                ventiBtn.setBackground(selectedColor);
                break;
        }
    }

    public void resetValue() {
        count = 1;
        totalPrice = 0;

        sizePriceModifier = 0;
        if (countLabel != null) {
            countLabel.setText(String.valueOf(count));
        }
    }
}