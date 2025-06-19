package client.admin;

import client.MainFrame;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.*;

public class StockCard extends JPanel {

    MyDialog dialog;
    MainFrame f;

    public StockCard(MainFrame f){
      this.f = f;

        JPanel stockPanel = new JPanel();
        JLabel s_Search = new JLabel("검색");
        JButton s_SearchBtn= new JButton();
        JScrollPane stockScroll = new JScrollPane();
        JTable stockTable = new JTable();

        ImageIcon icon = new ImageIcon("src/images/search.png");
        Image img = icon.getImage().getScaledInstance(
                21,21,Image.SCALE_SMOOTH);
        s_SearchBtn.setIcon(new ImageIcon(img));
        s_SearchBtn.setPreferredSize(new Dimension(21, 21));
        //jButton1.setBorder(new BevelBorder(BevelBorder.RAISED));
        s_SearchBtn.setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
        stockPanel.add(s_SearchBtn);
        stockPanel.add(s_Search);
        f.add(stockPanel);

    }
}
