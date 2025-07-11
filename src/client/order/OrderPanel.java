package client.order;

import client.MainFrame;
import vo.ProductsVO;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OrderPanel extends JPanel {

    private CategoryPanel categoryPanel;
    public MenuPanel menuPanel;
    private CartPanel cartPanel;
    JScrollPane menuScrollPane;
    int allPrice = 0;

    List<String[]> cartList = new ArrayList<>();

    ProductsVO p;
    MainFrame f;

    /**
     * OrderPanel의 생성자입니다.
     * @param f 메인 프레임(MainFrame)의 참조
     */
    public OrderPanel(MainFrame f) {
        this.f = f;

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
        menuScrollPane = new JScrollPane(menuPanel);
        menuScrollPane.setBorder(null); // 스크롤 패널의 테두리를 없애 깔끔하게 만듭니다.
        menuScrollPane.getVerticalScrollBar().setUnitIncrement(16); // 스크롤 속도를 적절하게 조절합니다.


        // 4. 각 패널을 프레임의 지정된 위치에 배치합니다.
        add(categoryPanel, BorderLayout.NORTH);  // 상단에 카테고리 패널
        add(menuScrollPane, BorderLayout.CENTER); // 중앙에 메뉴 패널
        add(cartPanel, BorderLayout.SOUTH); // 하단의 장바구니 패널

    }


    /**
     * OptionDialog의 '담기' 버튼을 누르면 수행하는 메소드.
     * @param product     상품 정보
     * @param count       수량
     * @param totalPrice  총가격
     * @param selectedSize 선택된 사이즈 (새로 추가된 파라미터)
     */
//    public void addToCart(ProductsVO product, int count, int totalPrice, String selectedSize) {
//        // --- 수정된 부분: 배열에 담는 순서를 [상품명, 사이즈, 수량, 가격]으로 변경 ---
//        String[] optionRow = {product.getP_name(), selectedSize, String.valueOf(count), String.valueOf(totalPrice)};
//
//        cartList.add(optionRow);
//
//        cartPanel.updateTable(); // 내용만 갱신
//        cartPanel.calTotalPrice(); // 총 가격 갱신
//        returnPrice = allPrice;
//    }

    // 만약 장바구니에 품목이 담겨있다면 해당 품목의 수량과 금액을 추가함
    public void addToCart(ProductsVO product, int count, int totalPrice, String selectedSize) {
        // --- 수정된 부분: 배열에 담는 순서를 [상품명, 사이즈, 수량, 가격]으로 변경 ---
        boolean found = false;

        for (int i = 0; i < cartList.size(); i++) {
            // 상품명과 옵션이 둘 다 동일한 경우
            if (cartList.get(i)[0].equals(product.getP_name()) && cartList.get(i)[1].equals(selectedSize)) {
                // 기존 수량, 금액에 더해서 누적
                int prevCount = Integer.parseInt(cartList.get(i)[2]);
                int prevPrice = Integer.parseInt(cartList.get(i)[3]);

                cartList.get(i)[2] = String.valueOf(prevCount + count);
                cartList.get(i)[3] = String.valueOf(prevPrice + totalPrice);

                found = true;
                break;
            }
        }        
  
        // 동일한 상품+옵션 조합이 없었으면 새로 추가
        if (!found) {
        String[] optionRow = {product.getP_name(), selectedSize, String.valueOf(count), String.valueOf(totalPrice),product.getP_code()};
        cartList.add(optionRow);
        }

        cartPanel.updateTable();      // 테이블 갱신
        cartPanel.calTotalPrice();    // 총 가격 갱신
    }
}