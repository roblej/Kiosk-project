package client.order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OptionDialog extends JDialog{

    public OptionDialog() {

        initcomponents();

        // 이벤트 감지자 등록
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose(); // 현재 창만 끄기
            }
        });

    }

    public void initcomponents() {

        JButton plusBtn, minusBtn;
        JButton okBtn, cancelBtn;
        JButton hotBtn, iceBtn;
        JPanel menuCenterPanel, countPanel, optionNorthPanel;
        JPanel northPanel, centerPanel, southPanel;
        JLabel imgLabel, countLabel, priceLabel;
        JLabel menuLabel;

        northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(1, 3)); // 상단의 이미지 / 메뉴 / price
        northPanel.setBackground(Color.green);
        centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        southPanel = new JPanel();
        southPanel.setLayout(new GridLayout(1, 2)); // 하단의 담기 / 취소
        menuCenterPanel = new JPanel();
        menuCenterPanel.setLayout(new GridLayout(2, 1)); // 상단 중앙의 메뉴이름 / - 1 +
        optionNorthPanel = new JPanel();
        optionNorthPanel.setLayout(new GridLayout(1, 2)); // 중앙 상단의 hot / ice

        // -------------------------------내용 수정 해야하는 부분--------------------------------
        imgLabel = new JLabel(new ImageIcon("이미지 주소")); // ************* 이미지 수정
        northPanel.add(imgLabel);

        menuLabel = new JLabel("메뉴이름");
        menuCenterPanel.add(menuLabel);

        countPanel = new JPanel();
        countPanel.setLayout(new GridLayout(1, 3));
        minusBtn = new JButton("-");
        countLabel = new JLabel("1");
        plusBtn = new JButton("+");
        countPanel.add(minusBtn);
        countPanel.add(countLabel);
        countPanel.add(plusBtn);
        menuCenterPanel.add(countPanel);
        northPanel.add(menuCenterPanel);

        priceLabel = new JLabel("price");
        northPanel.add(priceLabel);

        hotBtn = new JButton("hot");
        iceBtn = new JButton("ice");
        optionNorthPanel.add(hotBtn);
        optionNorthPanel.add(iceBtn);
        centerPanel.add(optionNorthPanel, BorderLayout.NORTH);

        okBtn = new JButton("담기");
        cancelBtn = new JButton("취소");
        southPanel.add(okBtn);
        southPanel.add(cancelBtn);

        this.add(northPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);

        setSize(450, 750); // 프레임보다 조금 작게 보여줌
        setLocationRelativeTo(null); // 창 화면 가운데 맞춤
        setVisible(true); // 보여주기

    }

    public static void main(String[] args) {
        new OptionDialog();
    }

}
