package client.order;

import client.MainFrame;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CategoryPanel extends JPanel {

    private final MenuPanel menuPanel;
    private String currentCategory;
    SqlSessionFactory factory;

    public CategoryPanel(MenuPanel menuPanel, MainFrame mainFrame) {
        this.menuPanel = menuPanel;
        super.setLayout(new BorderLayout());
        super.setBackground(Color.WHITE);
        this.factory= mainFrame.factory;
        this.currentCategory = "모든 메뉴";

        JPanel buttonContainerPanel = new JPanel(new GridLayout(0,4, 10, 10));
        buttonContainerPanel.setBackground(Color.WHITE);

        buttonContainerPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        JButton allButton = new JButton("모든 메뉴");
        allButton.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        allButton.addActionListener(e -> {
            if (!"모든 메뉴".equals(currentCategory)) {
                this.currentCategory = "모든 메뉴";
                this.menuPanel.updateMenus("모든 메뉴");
            }
        });
        buttonContainerPanel.add(allButton);

        List<String> categories = getCategories();
        if (categories != null && !categories.isEmpty()) {
            for (String categoryName : categories) {
                JButton categoryButton = new JButton(categoryName);
                categoryButton.setFont(new Font("맑은 고딕", Font.BOLD, 14));

                categoryButton.addActionListener(e -> {
                    if (!categoryName.equals(currentCategory)) {
                        this.currentCategory = categoryName;
                        this.menuPanel.updateMenus(categoryName);
                    }
                });
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

    public List<String> getCategories() {
        try (SqlSession session = factory.openSession()) {
            return session.selectList("products.getCategories");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}