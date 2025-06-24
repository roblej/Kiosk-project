package client.order;

import client.MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CouponPanel extends JPanel {

    MainFrame f;

    JPanel north_p, south_p, center_p;
    JButton payment_bt, back_bt;
    JTable payment_table;
    String[] c_data = {"상품명","수량","가격","할인율","총결제금액"};
    String[][] data;

    public CouponPanel(MainFrame f){

        this.f = f;

        north_p = new JPanel();
        south_p = new JPanel();
        center_p = new JPanel();
        back_bt = new JButton("뒤로");
        payment_bt = new JButton("결제");

        north_p.add(new JLabel("주문내역확인"));
        JSeparator Js = new JSeparator();
        Js.setPreferredSize(new Dimension(1,10));
        north_p.add(Js);
        this.add(north_p);

        this.add(new JScrollPane(payment_table = new JTable()));
        payment_table.setModel(new DefaultTableModel(data,c_data));
        this.add(center_p);

        south_p.add(back_bt);
        south_p.add(payment_bt);

        this.add(south_p);


        this.setBounds(500, 150, 500, 800);
        //this.setVisible(true);

        back_bt.addActionListener(e -> cliked_back());

        payment_bt.addActionListener(e -> cliked_payment());

    }

    public void cliked_payment(){
        f.cardLayout.show(f.cardPanel,"PaymentPanel");
    }

    public void cliked_back(){
        f.cardLayout.show(f.cardPanel, "orderPanel");
    }

}
