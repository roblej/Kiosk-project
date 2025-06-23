package client.order;

import client.MainFrame;
import org.apache.ibatis.session.SqlSession;
import vo.ProductsVO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MenuPanel extends JPanel {

    OrderPanel orderPanel;
    MainFrame f;
    // ProductsDao를 멤버 변수로 추가
    private ProductsDao productsDao;

    public MenuPanel(OrderPanel orderPanel, MainFrame f, ProductsVO p) {
        this.orderPanel = orderPanel;
        this.f = f;
        this.productsDao = new ProductsDao(f.factory); // DAO 생성

        setBackground(Color.WHITE);
        setLayout(new GridLayout(0, 3, 15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        getData();
    }

    // 기존 메소드 원본 유지(함수 이름만 변경)
    public void getData() {
        SqlSession ss = f.factory.openSession();
        // 이름를 변경했으므로 "products.getAllProducts"를 사용
        List<ProductsVO> list = ss.selectList("products.getAllProducts");
        ss.close();

        for (ProductsVO vo : list) {
            MenuButton btn = new MenuButton(vo.getP_name());
            btn.addActionListener(e -> new OptionDialog(orderPanel, f, vo));
            add(btn);
        }
        revalidate();
        repaint();
    }

    // CategoryPanel에서 호출할 메뉴 업데이트 메소드 추가
    public void updateMenus(String category) {
        removeAll(); // 먼저 현재 메뉴창에 떠있는 메뉴들 모두 제거함

        List<ProductsVO> productList;
        if (category.equals("모든 메뉴")) { // 모든 메뉴에 있는 모든 메뉴 리스트임
            productList = productsDao.getAllProducts();
        } else { // 아닐 경우
            productList = productsDao.getProductsByCategory(category);
        }

        if (productList != null) {
            for (ProductsVO vo : productList) {
                MenuButton btn = new MenuButton(vo.getP_name());
                btn.addActionListener(e -> new OptionDialog(orderPanel, f, vo));
                add(btn);
            }
        }

        revalidate(); // 레이아웃 새로고침
        repaint();    // 패널 다시 그리기
    }
}