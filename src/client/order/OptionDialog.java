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
    private String selectedSize = "Tall";
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

        okBtn.addActionListener(e -> {
            p.addToCart(product, count, totalPrice);
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

        // --- 수정 1: 이미지를 래퍼 패널로 감싸 정사각형 유지 ---
        imgLabel = new JLabel("이미지", SwingConstants.CENTER);
        imgLabel.setPreferredSize(new Dimension(120, 120)); // 정사각형 크기 지정
        imgLabel.setOpaque(true);
        imgLabel.setBackground(Color.gray);
        imgLabel.setForeground(Color.WHITE);

        JPanel imageWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        imageWrapper.add(imgLabel);
        northPanel.add(imageWrapper); // 패널에 이미지 라벨이 아닌 래퍼 패널을 추가

        // 메뉴이름 & 수량
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

        // 가격 영역
        priceLabel = new JLabel("", SwingConstants.CENTER);
        priceLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        northPanel.add(priceLabel);

        // ================= 중앙 패널 (옵션) =================
        centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // 사이즈 버튼 패널 (래퍼로 감싸서 크기 유지)
        JPanel sizePanel = new JPanel(new GridLayout(1, 3, 10, 0));
        shortBtn = new JButton("Short (-500)");
        tallBtn = new JButton("Tall (기본)");
        ventiBtn = new JButton("Venti (+500)");
        sizePanel.add(shortBtn);
        sizePanel.add(tallBtn);
        sizePanel.add(ventiBtn);

        JPanel sizeWrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        sizeWrapperPanel.add(sizePanel);
        centerPanel.add(sizeWrapperPanel, BorderLayout.CENTER); // 중앙에 배치

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

        // --- 수정 3: 전체 창 크기 조절 ---
        setSize(500, 600); // 다이얼로그 크기를 줄여서 간결하게 만듦
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
        selectedSize = "Tall";
        sizePriceModifier = 0;
        if (countLabel != null) {
            countLabel.setText(String.valueOf(count));
        }
    }
}