package client.order;

import client.MainFrame;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CategoryPanel extends JPanel {

    public CategoryPanel(MainFrame mainFrame) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(Color.WHITE);

        // DAO를 생성하여 DB에서 카테고리 목록을 조회합니다.
        ProductsDao productsDao = new ProductsDao(mainFrame.factory);
        List<String> categories = productsDao.selectDistinctCategories();

        // 조회된 카테고리로 버튼을 생성합니다.
        if (categories != null && !categories.isEmpty()) {
            for (String categoryName : categories) {
                JButton categoryButton = new JButton(categoryName);
                categoryButton.setFont(new Font("맑은 고딕", Font.BOLD, 14));
                // 다른 패널에 기능이 없으므로 ActionListener는 추가하지 않습니다.
                add(categoryButton);
            }
        } else {
            // 카테고리가 없을 경우의 메시지
            add(new JLabel("표시할 카테고리가 없습니다."));
        }
    }
}