package client.order;

import client.MainFrame;
import vo.ProductsVO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class OptionDialog extends JDialog {

    // 장바구니에서 사용해야하는 값들을 받는 멤버변수들
    int totalPrice = 0;
    int count = 1;

    ProductsVO product;
    JButton plusBtn, minusBtn;
    JButton okBtn, cancelBtn;
    int price = 0;
    JButton hotBtn, iceBtn; // 옵션 버튼
    JPanel menuCenterPanel, countPanel, optionNorthPanel;
    JPanel northPanel, centerPanel, southPanel;
    JLabel imgLabel, countLabel, priceLabel;
    JLabel menuLabel;

    OrderPanel p;
    MainFrame f;

//        public CartPanel(OrderPanel orderPanel, MainFrame f) {

    public OptionDialog(OrderPanel p, MainFrame f, ProductsVO product) {
        this.p = p;
        this.f = f;
        this.product = product;
//        this.defaultPrice = Integer.parseInt(product.getP_price()); // String형 price 숫자로 변환

        // 가격 초기값 설정
        resetValue();
        calPrice();

        // 창 보이게 하기
        initcomponents();

        // 창 닫기 이벤트
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // 담기
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 장바구니에 값 담고 창 꺼지기
                p.addToCart(product, count, totalPrice); // 함수 호출
                dispose();
            }
        });

        // 취소
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // 수량 더하기, 빼기
        plusBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                count++;
                updateCount();
                calPrice();
            }
        });
        minusBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(count > 1){
                    count--;
                    updateCount();
                    calPrice();
                }
            }
        });
    }

    public void initcomponents() {

        // ================= 상단 패널 =================
        northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(1, 3));
        northPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // 이미지
        imgLabel = new JLabel();
        imgLabel.setPreferredSize(new Dimension(80, 80));
        imgLabel.setOpaque(true);
        imgLabel.setBackground(Color.gray);
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imgLabel.setVerticalAlignment(SwingConstants.TOP);
        imgLabel.setForeground(Color.WHITE);
        imgLabel.setText("이미지");
        northPanel.add(imgLabel);

        // 메뉴이름 & 수량
        menuCenterPanel = new JPanel();
        menuCenterPanel.setLayout(new GridLayout(2, 1, 5, 5));

        // 메뉴 이름 라벨 가져오기
        menuLabel = new JLabel(product.getP_name(), SwingConstants.CENTER);
        menuLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        menuLabel.setOpaque(true);
        menuCenterPanel.add(menuLabel);


        countPanel = new JPanel(new GridLayout(1, 3, 5, 0));
        minusBtn = new JButton("-");
        minusBtn.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        countLabel = new JLabel("1",SwingConstants.CENTER);
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
        priceLabel = new JLabel(product.getP_price(), SwingConstants.CENTER);
        priceLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        priceLabel.setOpaque(true);
        northPanel.add(priceLabel);

        // ================= 중앙 패널 =================
        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // hot ice 버튼
//        optionNorthPanel = new JPanel();
//        optionNorthPanel.setLayout(new GridLayout(1, 2, 10, 0));
//        hotBtn = new JButton("hot");
//        iceBtn = new JButton("ice");
//        optionNorthPanel.add(hotBtn);
//        optionNorthPanel.add(iceBtn);
//
//        centerPanel.add(optionNorthPanel, BorderLayout.NORTH);

        JTextArea optionArea = new JTextArea("옵션");
        optionArea.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        optionArea.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        optionArea.setEditable(false);
        centerPanel.add(optionArea, BorderLayout.CENTER);

        // ================= 하단 패널 =================
        southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(1, 2, 10, 0));
        southPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        okBtn = new JButton("담기");
        cancelBtn = new JButton("취소");
        southPanel.add(okBtn);
        southPanel.add(cancelBtn);

        // ================= 프레임 조립 =================
        this.add(northPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);

        setSize(450, 750);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void updateCount(){
        countLabel.setText(String.valueOf(count));
    }

    // Label에 가격 보이게하기
    public void calPrice(){ // cakculatePrice
        if(priceLabel != null) // 창을 생성할 때 초기화 하므로 값이 비어있다면 수행하지 않아야 함
            priceLabel.setText(String.valueOf(count*Integer.parseInt(product.getP_price())));
        totalPrice = count*price;
    }

    public void resetValue(){
        count = 1;
        price = Integer.parseInt(product.getP_price());
        totalPrice = 0;
        if(priceLabel != null) // 창을 생성할 때 초기화 하므로 값이 비어있다면 수행하지 않아야 함
            priceLabel.setText(String.valueOf(price));
    }
}
