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

    public OptionDialog(OrderPanel p, MainFrame f, boolean modal, ProductsVO product) {

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
        if(!product.getP_category().equals("디저트")){
            shortBtn.addActionListener(e -> handleSizeSelection("Short", -500));
            tallBtn.addActionListener(e -> handleSizeSelection("Tall", 0));
            ventiBtn.addActionListener(e -> handleSizeSelection("Venti", 500));
        }
    }

    public void initcomponents() {
        // ================= 상단 패널 =================
        northPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        northPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        northPanel.setBackground(Color.white);

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
        imageWrapper.setBackground(Color.white);
        menuCenterPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        menuLabel = new JLabel(product.getP_name(), SwingConstants.CENTER);
        menuLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        menuCenterPanel.add(menuLabel);
        menuCenterPanel.setBackground(Color.white);

        countPanel = new JPanel(new GridLayout(1, 3, 0, 0));

        // PLUS 버튼
        ImageIcon rawPlusIcon = new ImageIcon("src/images/plus.jpg");
        Image plusImg = rawPlusIcon.getImage();
        Image scaledPlusImg = plusImg.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon plusIcon = new ImageIcon(scaledPlusImg);

        plusBtn = new JButton(plusIcon);

        // 버튼 프레임 숨기기
        plusBtn.setBorderPainted(false);
        plusBtn.setContentAreaFilled(false);
        plusBtn.setFocusPainted(false);
        plusBtn.setOpaque(false);

        countLabel = new JLabel("1", SwingConstants.CENTER);
        countLabel.setOpaque(true);
        countLabel.setFont(new Font("맑은 고딕", Font.BOLD, 17));
        countLabel.setBackground(Color.white);

        // MINUS 버튼
        ImageIcon rawMinusIcon = new ImageIcon("src/images/minus.png");
        Image minusImg = rawMinusIcon.getImage();
        Image scaledMinusImg = minusImg.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon minusIcon = new ImageIcon(scaledMinusImg);

        minusBtn = new JButton(minusIcon);

        // 버튼 프레임 숨기기
        minusBtn.setBorderPainted(false);
        minusBtn.setContentAreaFilled(false);
        minusBtn.setFocusPainted(false);
        minusBtn.setOpaque(false);

        countPanel.add(minusBtn);
        countPanel.add(countLabel);
        countPanel.add(plusBtn);
        countPanel.setBackground(Color.white);
        menuCenterPanel.add(countPanel);
        northPanel.add(menuCenterPanel);

        priceLabel = new JLabel("", SwingConstants.CENTER);
        priceLabel.setFont(new Font("맑은 고딕", Font.BOLD, 22));
        northPanel.add(priceLabel);

        // ================= 중앙 패널 (옵션) =================
        centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));
        centerPanel.setBackground(Color.white);
        if(!product.getP_category().equals("디저트")) {

            JPanel sizePanel = new JPanel(new GridLayout(1, 3, 10, 0));
            sizePanel.setBackground(Color.white);
            shortBtn = new JButton("Short (-500)");
            tallBtn = new JButton("Tall (기본)");
            ventiBtn = new JButton("Venti (+500)");
            sizePanel.add(shortBtn);
            sizePanel.add(tallBtn);
            sizePanel.add(ventiBtn);
            JPanel sizeWrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            sizeWrapperPanel.setBackground(Color.white);
            sizeWrapperPanel.add(sizePanel);
            centerPanel.add(sizeWrapperPanel, BorderLayout.CENTER);
            updateSizeButtonUI();
        }

        // ================= 하단 패널 (버튼) =================
        southPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        southPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        southPanel.setBackground(Color.white);
        okBtn = new JButton("담기");
        cancelBtn = new JButton("취소");
        southPanel.add(okBtn);
        southPanel.add(cancelBtn);

        // ================= 프레임 조립 =================
        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        setSize(450, 680); // 다이얼로그 높이 조절
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
        if(!product.getP_category().equals("디저트")) {
            selectedSize = "Tall"; // 기본 사이즈를 Tall로 설정
        }
        sizePriceModifier = 0;
        if (countLabel != null) {
            countLabel.setText(String.valueOf(count));
        }
    }
}