package client.order; // 패키지 경로는 프로젝트에 맞게 확인해주세요.

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
    MainFrame f;

    int returnPrice;
    /**
     * OrderPanel의 생성자입니다.
     * @param f 메인 프레임(MainFrame)의 참조
     */
    public OrderPanel(MainFrame f) {
        this.f = f;

        returnPrice = 0;
        // 1. 패널의 레이아웃을 BorderLayout으로 설정합니다.
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 2. 자식 패널들의 인스턴스를 생성합니다.
        //    - CategoryPanel이 MenuPanel을 제어해야 하므로, MenuPanel을 먼저 생성해야 합니다.
        //    - 기존 MenuPanel 생성자를 그대로 활용합니다.
        menuPanel = new MenuPanel(this, f, p);
        cartPanel = new CartPanel(f, this, cartList);

        //    - CategoryPanel을 생성할 때, 제어할 대상인 menuPanel의 참조를 넘겨줍니다.
        categoryPanel = new CategoryPanel(menuPanel, f);

        // 3. MenuPanel은 내용이 많아질 수 있으므로 스크롤 가능하도록 JScrollPane에 추가합니다.
        JScrollPane menuScrollPane = new JScrollPane(menuPanel);
        menuScrollPane.setBorder(null); // 스크롤 패널의 테두리를 없애 깔끔하게 만듭니다.
        menuScrollPane.getVerticalScrollBar().setUnitIncrement(16); // 스크롤 속도를 적절하게 조절합니다.


        // 4. 각 패널을 프레임의 지정된 위치에 배치합니다.
        add(categoryPanel, BorderLayout.NORTH);  // 상단에 카테고리 패널
        add(menuScrollPane, BorderLayout.CENTER); // 중앙에 메뉴 패널
        add(cartPanel, BorderLayout.SOUTH); // 하단의 장바구니 패널

    }

    // --- 앞으로 모든 이벤트 처리 및 로직 메소드들은 이 클래스에 추가 ---
    // 예: public void loadMenus(String category) { ... }
    //     public void showOptionDialog(ProductVO product) { ... }

    // OptjonDialog의 담기 버튼을 누르면 수행하는 addToCart, cartPanel에 접근하기 위해 이곳에서 수행
    public void addToCart(ProductsVO product, int count, int totalPrice) {
        allPrice = allPrice + totalPrice;

//         이미 장바구니에 담겨있는 상품은 새로 만들지 않고 있는 값에 추가한다.
//        if(cartPanel.data){
//
//        }

        String[] optionRow = {product.getP_name(), String.valueOf(count), String.valueOf(totalPrice), "옵션"};
        cartList.add(optionRow);

        cartPanel.updateTable(); // 내용만 갱신
        cartPanel.updatePrice(allPrice); // 총 가격 갱신
        returnPrice = allPrice;
        revalidate();
        repaint();
    }

}