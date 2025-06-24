package client.order;

import client.MainFrame;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CategoryPanel extends JPanel {

    private final MenuPanel menuPanel;

    public CategoryPanel(MenuPanel menuPanel, MainFrame mainFrame) {
        this.menuPanel = menuPanel;
        super.setLayout(new BorderLayout());
        super.setBackground(Color.WHITE);

        JPanel buttonContainerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        buttonContainerPanel.setBackground(Color.WHITE);

        // --- 수정된 부분 ---
        // 이전의 불필요하게 큰 하단 여백 대신, 위아래로 작은 여백(5px)을 주어 간격을 조절합니다.
        buttonContainerPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        // --- 여기까지 수정 ---

        ProductsDao productsDao = new ProductsDao(mainFrame.factory);

        JButton allButton = new JButton("모든 메뉴");
        allButton.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        allButton.addActionListener(e -> this.menuPanel.updateMenus("모든 메뉴"));
        buttonContainerPanel.add(allButton);

        List<String> categories = productsDao.getCategories();
        if (categories != null && !categories.isEmpty()) {
            for (String categoryName : categories) {
                JButton categoryButton = new JButton(categoryName);
                categoryButton.setFont(new Font("맑은 고딕", Font.BOLD, 14));
                categoryButton.addActionListener(e -> this.menuPanel.updateMenus(categoryName));
                buttonContainerPanel.add(categoryButton);
            }
        } else {
            buttonContainerPanel.add(new JLabel("표시할 카테고리가 없습니다."));
        }

        JScrollPane scrollPane = new JScrollPane(buttonContainerPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);

        super.add(scrollPane, BorderLayout.CENTER);
    }
}