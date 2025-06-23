package client.order; // 새로운 패키지 경로

import client.MainFrame;
import vo.ProductsVO;

import javax.swing.*;
import java.awt.*;

public class OrderPanel extends JPanel {

    private CategoryPanel categoryPanel;
    private MenuPanel menuPanel;
    private CartPanel cartPanel;

    ProductsVO p;
    OptionDialog d;
    MainFrame f;

    public OrderPanel(MainFrame f) {
        this.f = f;
        // 1. 이 패널의 레이아웃을 BorderLayout으로 설정.
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 2. 자식 패널들의 인스턴스를 생성.
        // (자식 패널들도 OrderPanel을 인식하도록 생성자를 수정.)
        categoryPanel = new CategoryPanel(f);
        menuPanel = new MenuPanel(this, f, p);
//        cartPanel = new CartPanel(this, d, p);

        // 3. MenuPanel은 내용이 많아질 수 있으므로 스크롤 가능하도록 JScrollPane에 추가.
        JScrollPane menuScrollPane = new JScrollPane(menuPanel);
        menuScrollPane.setBorder(null); // 스크롤 패널의 테두리를 없애 깔끔하게 만듬.

        // 4. 각 패널을 지정된 위치에 배치.
        add(categoryPanel, BorderLayout.NORTH);  // 상단에 카테고리 패널
        add(menuScrollPane, BorderLayout.CENTER); // 중앙에 메뉴 패널
//        add(cartPanel, BorderLayout.SOUTH);   // 하단에 카트 패널
    }

    // --- 앞으로 모든 이벤트 처리 및 로직 메소드들은 이 클래스에 추가 ---
    // 예: public void loadMenus(String category) { ... }
    //     public void showOptionDialog(ProductVO product) { ... }

    public void addToCart(MainFrame f,ProductsVO product, int totalPrice) {
        if (cartPanel != null) remove(cartPanel); // 이전 장바구니 제거
        cartPanel = new CartPanel(f,this, product, totalPrice);
        add(cartPanel, BorderLayout.SOUTH);
        revalidate(); // 레이아웃 갱신
        repaint();    // 화면 갱신
    }

}