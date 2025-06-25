package client.order;

import client.MainFrame;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import vo.ProductsVO;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MenuPanel extends JPanel {

    private final OrderPanel orderPanel;
    private final MainFrame f;

    private final JPanel gridPanel;

    private OptionDialog optionDialog;
    private SqlSessionFactory factory;

    public MenuPanel(OrderPanel orderPanel, MainFrame f, ProductsVO p) {
        this.orderPanel = orderPanel;
        this.f = f;
        this.factory = f.factory;

        super.setLayout(new BorderLayout());
        super.setBackground(Color.WHITE);

        // --- 1. 그리드 레이아웃의 좌우 간격(hgap) 수정 ---
        gridPanel = new JPanel(new GridLayout(0, 3, 10, 15)); // 가로 간격 15 -> 10
        gridPanel.setBackground(Color.WHITE);
        // --- 2. 그리드 패널의 테두리 여백 수정 ---
        gridPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10)); // 좌우 여백 15 -> 10

        super.add(gridPanel, BorderLayout.NORTH);

        getData();
    }

    private void addMenuButton(ProductsVO vo) {
        MenuButton btnPanel = new MenuButton(vo);

        btnPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // 메뉴버튼 클릭했을 때
                // 이미 열려있다면 새로 열지않음
                if (optionDialog != null && optionDialog.isShowing()) {
                    return;
                }
                // ✅ 새로 열고, 닫힐 때 optionDialog = null 처리
                optionDialog = new OptionDialog(orderPanel, f, vo);
                optionDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        optionDialog = null;
                    }
                });
            }
            @Override
            public void mouseEntered(MouseEvent e) { // 메뉴버튼에 마우스 갖다댔을 때
                btnPanel.setBorder(new LineBorder(Color.BLUE, 2));
            }
            @Override
            public void mouseExited(MouseEvent e) { // 메뉴버튼에서 마우스 나갔을 때
                btnPanel.setBorder(new LineBorder(new Color(220, 220, 220)));
            }
        });

        JPanel wrapper = new JPanel();
        wrapper.setOpaque(false);
        wrapper.add(btnPanel);

        gridPanel.add(wrapper);
    }

    public void getData() {
        SqlSession ss = f.factory.openSession();
        List<ProductsVO> list = ss.selectList("products.all");
        ss.close();

        if (list != null) {
            for (ProductsVO vo : list) {
                addMenuButton(vo);
            }
        }
        super.revalidate();
        super.repaint();
    }

    // 1. 모든 상품 조회하는 메소드
    public List<ProductsVO> all() {
        try (SqlSession session = factory.openSession()) {
            return session.selectList("products.all");
        }
    }

    // 2. 카테고리별 상품 조회 메소드
    public List<ProductsVO> getProductsByCategory(String category) {
        try (SqlSession session = factory.openSession()) {
            return session.selectList("products.getProductsByCategory", category);
        }
    }
    public void updateMenus(String category) {
        gridPanel.removeAll();

        List<ProductsVO> productList = category.equals("모든 메뉴") ? all() : getProductsByCategory(category);

        if (productList != null) {
            for (ProductsVO vo : productList) {
                addMenuButton(vo);
            }
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }
}