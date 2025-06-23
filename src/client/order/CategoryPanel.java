package client.order;

import client.MainFrame;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CategoryPanel extends JPanel {

    // 제어할 MenuPanel을 저장할 멤버 변수
    private MenuPanel menuPanel;

    // 생성자에서 MenuPanel을 인자로 추가로 받아오도록 수정
    public CategoryPanel(MenuPanel menuPanel, MainFrame mainFrame) {
        this.menuPanel = menuPanel; // 전달받은 MenuPanel 인스턴스를 저장

        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(Color.WHITE);

        // DAO를 생성하여 DB에서 카테고리 목록을 조회
        ProductsDao productsDao = new ProductsDao(mainFrame.factory);

        // 1. "모든 메뉴" 버튼 생성 및 리스너 추가
        JButton allButton = new JButton("모든 메뉴");
        allButton.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        allButton.addActionListener(e -> {
            // MenuPanel의 updateMenus 메소드 호출
            this.menuPanel.updateMenus("모든 메뉴");
        });
        add(allButton);

        // 2. DB에서 가져온 카테고리들로 버튼 생성 및 리스너 추가
        List<String> categories = productsDao.getCategories();
        if (categories != null && !categories.isEmpty()) {
            for (String categoryName : categories) {
                JButton categoryButton = new JButton(categoryName);
                categoryButton.setFont(new Font("맑은 고딕", Font.BOLD, 14));

                // 각 카테고리 버튼에 ActionListener 추가
                categoryButton.addActionListener(e -> {
                    // MenuPanel의 updateMenus 메소드 호출
                    this.menuPanel.updateMenus(categoryName);
                });
                add(categoryButton);
            }
        } else {
            add(new JLabel("표시할 카테고리가 없습니다."));
        }
    }
}