package client.order;

import vo.ProductsVO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OptionDialog extends JDialog {

    ProductsVO product;
    JButton plusBtn, minusBtn;
    JButton okBtn, cancelBtn;
    int count = 1;
    int totalPrice = 0;
    JButton hotBtn, iceBtn;
    JPanel menuCenterPanel, countPanel, optionNorthPanel;
    JPanel northPanel, centerPanel, southPanel;
    JLabel imgLabel, countLabel, priceLabel;
    JLabel menuLabel;

    public OptionDialog(ProductsVO product) {
        this.product = product;
//        this.defaultPrice = Integer.parseInt(product.getP_price()); // String형 price 숫자로 변환
        initcomponents();

        // 창 닫기 이벤트
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 장바구니에 값 담고 창 꺼지기
                
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
        plusBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                count++;
                updateCount();
                updatePrice();
                System.out.println();
            }
        });
        minusBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(count > 1){
                    count--;
                    updateCount();
                    updatePrice();
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
        imgLabel.setBackground(Color.BLACK);
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imgLabel.setVerticalAlignment(SwingConstants.TOP);
        imgLabel.setForeground(Color.WHITE);
        imgLabel.setText("이미지");
        northPanel.add(imgLabel);

        // 메뉴이름 & 수량
        menuCenterPanel = new JPanel();
        menuCenterPanel.setLayout(new GridLayout(2, 1, 5, 5));

        menuLabel = new JLabel("메뉴이름", SwingConstants.CENTER);
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

        // 가격
//        priceLabel = new JLabel(product.getP_price(), SwingConstants.CENTER);
        priceLabel = new JLabel("4500", SwingConstants.CENTER);
        priceLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
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

    public void updatePrice(){
//        totalPrice = Integer.parseInt(product.getP_price()) * count; // price 연결 시 현재 count 와 연산
        totalPrice = 4500 * count;
    }



    public static void main(String[] args) {
        new OptionDialog(null);
    }
}
