package client.order;

import client.MainFrame;

import javax.swing.*;

public class PaymentPanel extends JPanel {

    MainFrame f;

    public PaymentPanel(MainFrame f){
        this.f = f;

        this.add(new JLabel("결제과 완료되었습니다"));
    }
}
