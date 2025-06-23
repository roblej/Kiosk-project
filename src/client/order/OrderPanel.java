package client.order; // 새로운 패키지 경로

import client.MainFrame;
import vo.ProductsVO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OrderPanel extends JPanel {

    private CategoryPanel categoryPanel;
    private MenuPanel menuPanel;
    private CartPanel cartPanel;
    int allPrice = 0;

    List<String[]> cartList = new ArrayList<>();

    ProductsVO p;

    public OrderPanel(MainFrame f) {
        // 1. 이 패널의 레이아웃을 BorderLayout으로 설정.
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 2. 자식 패널들의 인스턴스를 생성.
        // (자식 패널들도 OrderPanel을 인식하도록 생성자를 수정.)
        categoryPanel = new CategoryPanel(f);
        menuPanel = new MenuPanel(this, f, p);
        cartPanel = new CartPanel(this, cartList);

        // 3. MenuPanel은 내용이 많아질 수 있으므로 스크롤 가능하도록 JScrollPane에 추가.
        JScrollPane menuScrollPane = new JScrollPane(menuPanel);
        menuScrollPane.setBorder(null); // 스크롤 패널의 테두리를 없애 깔끔하게 만듬.

        // 4. 각 패널을 지정된 위치에 배치.
        add(categoryPanel, BorderLayout.NORTH);  // 상단에 카테고리 패널
        add(menuScrollPane, BorderLayout.CENTER); // 중앙에 메뉴 패널
        add(cartPanel, BorderLayout.SOUTH); // 하단의 장바구니 패널

    }

    // --- 앞으로 모든 이벤트 처리 및 로직 메소드들은 이 클래스에 추가 ---
    // 예: public void loadMenus(String category) { ... }
    //     public void showOptionDialog(ProductVO product) { ... }

    public void addToCart(ProductsVO product, int count, int totalPrice) {
        allPrice = allPrice + totalPrice;
        String[] optionRow = {product.getP_name(), String.valueOf(count), String.valueOf(totalPrice), "옵션"};
        cartList.add(optionRow);

        if (cartPanel == null) {
            cartPanel = new CartPanel(this, cartList);
            add(cartPanel, BorderLayout.SOUTH); // 처음 생성 시 패널 붙이기
        } else {
            cartPanel.updateTable(); // 내용만 갱신
            cartPanel.updatePrice(allPrice);
        }

        revalidate();
        repaint();
    }
}