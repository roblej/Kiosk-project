package client.order; // 패키지 변경

import client.MainFrame;
import vo.ProductsVO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.border.MatteBorder;

/*
이곳은 OptionDialog에서 저장된 값의 정보가 담길 class이다
OptionDialog에서 전달하고자 하는 이름, 가격을 멤버변수로 만들어야하고
이곳의 결제하기 버튼을 눌렀을 때 CartPanel의 List는 초기화 되어야하며 쿠폰 사용 유무 창을 띄워야한다.
 */

public class CartPanel extends JPanel {

    private OrderPanel orderPanel; // MainFrame 대신 OrderPanel을 참조
    List<ProductsVO> productsList;
    OptionDialog d;
    ProductsVO p;

    // 생성자에서 MainFrame 대신 OrderPanel을 받도록 수정
    public CartPanel(OrderPanel orderPanel, ProductsVO vo, int totalPrice) {
        this.orderPanel = orderPanel;
        this.d = d;
        this.p = vo;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500, 220));
        setBackground(Color.WHITE);
        setBorder(new MatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));

        // (이하 장바구니 UI 구성 요소... - 이전 코드와 유사)
        add(new JLabel("장바구니: " + vo.getP_name(), SwingConstants.CENTER), BorderLayout.NORTH);

        productsList = new ArrayList<>();
        productsList.add(vo);


        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(new JLabel("총 금액: " + totalPrice + "원"), BorderLayout.CENTER);
        bottomPanel.add(new JButton("결제하기"), BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        new CartPanel(null, null, 0);
    }

}