package client.order;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuButton extends JButton {

    public MenuButton(String name, int price) {
        setBackground(Color.WHITE);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setBorder(new LineBorder(new Color(220, 220, 220)));
        setFocusPainted(false);

        ImageIcon placeholderIcon = createPlaceholderIcon(120, 120, new Color(230, 230, 230));
        setIcon(placeholderIcon);

        String htmlText = String.format(
                "<html><center><p style='margin-bottom:3px;'>%s</p>" +
                        "<p style='color:rgb(80,80,80);'>%,d원</p></center></html>",
                name,
                price
        );
        setText(htmlText);
        setFont(new Font("맑은 고딕", Font.BOLD, 14));
        setVerticalTextPosition(SwingConstants.BOTTOM);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setIconTextGap(10);
    }

    private ImageIcon createPlaceholderIcon(int width, int height, Color color) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setColor(color);
        g2d.fillRect(0, 0, width, height);
        g2d.dispose();
        return new ImageIcon(image);
    }
}