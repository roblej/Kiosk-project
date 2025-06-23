package client.order; // 패키지 경로는 프로젝트에 맞게 확인해주세요.

import client.MainFrame;
import vo.ProductsVO;

import javax.swing.*;
import java.awt.*;

/**
 * 주문 화면의 메인 컨테이너 패널입니다.
 * 상단에는 CategoryPanel, 중앙에는 MenuPanel, 하단에는 CartPanel이 위치합니다.
 */
public class OrderPanel extends JPanel {

    private CategoryPanel categoryPanel;
    private MenuPanel menuPanel;
    private CartPanel cartPanel;

    // 이 필드들은 MenuPanel 생성자에 필요함
    private ProductsVO p;
    private OptionDialog d;

    /**
     * OrderPanel의 생성자입니다.
     * @param f 메인 프레임(MainFrame)의 참조
     */
    public OrderPanel(MainFrame f) {
        // 1. 패널의 레이아웃을 BorderLayout으로 설정합니다.
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 2. 자식 패널들의 인스턴스를 생성합니다.
        //    - CategoryPanel이 MenuPanel을 제어해야 하므로, MenuPanel을 먼저 생성해야 합니다.
        //    - 기존 MenuPanel 생성자를 그대로 활용합니다.
        menuPanel = new MenuPanel(this, f, p);

        //    - CategoryPanel을 생성할 때, 제어할 대상인 menuPanel의 참조를 넘겨줍니다.
        categoryPanel = new CategoryPanel(menuPanel, f);

        // 3. MenuPanel은 내용이 많아질 수 있으므로 스크롤 가능하도록 JScrollPane에 추가합니다.
        JScrollPane menuScrollPane = new JScrollPane(menuPanel);
        menuScrollPane.setBorder(null); // 스크롤 패널의 테두리를 없애 깔끔하게 만듭니다.
        menuScrollPane.getVerticalScrollBar().setUnitIncrement(16); // 스크롤 속도를 적절하게 조절합니다.


        // 4. 각 패널을 프레임의 지정된 위치에 배치합니다.
        add(categoryPanel, BorderLayout.NORTH);  // 상단에 카테고리 패널
        add(menuScrollPane, BorderLayout.CENTER); // 중앙에 스크롤 가능한 메뉴 패널
        // add(cartPanel, BorderLayout.SOUTH);   // 장바구니 패널은 addToCart 시점에 추가됩니다.
    }

    /**
     * 장바구니에 상품을 추가하고 CartPanel을 화면 하단에 표시하거나 갱신합니다.
     * @param product   장바구니에 추가될 상품 정보
     * @param totalPrice 옵션을 포함한 최종 가격
     */
    public void addToCart(ProductsVO product, int totalPrice) {
        // 기존에 cartPanel이 있다면 먼저 제거합니다.
        if (cartPanel != null) {
            remove(cartPanel);
        }
        // 새로운 정보로 CartPanel을 생성합니다. (CartPanel 구현에 따라 생성자 변경 필요)
        cartPanel = new CartPanel(this, product, totalPrice);

        // 패널을 하단에 추가합니다.
        add(cartPanel, BorderLayout.SOUTH);

        // 레이아웃을 갱신하고 화면을 다시 그려 변경사항을 즉시 반영합니다.
        revalidate();
        repaint();
    }
}