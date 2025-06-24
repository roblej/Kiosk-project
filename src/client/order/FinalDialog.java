package client.order;

import client.MainFrame;

import javax.swing.*;
import java.awt.*;

public class FinalDialog extends JDialog {

    JDialog FinalDialog;
    JButton confirmBt;
    MainFrame f;

    CartPanel p;

    public FinalDialog(MainFrame f, CartPanel p){

        this.f = f;
        this.p = p;
        FinalDialog = new JDialog();
        FinalDialog.add(new JLabel("결제가 완료되었습니다"));

        FinalDialog.add(confirmBt = new JButton("확인"), BorderLayout.SOUTH);

        FinalDialog.setSize(200, 150);
        FinalDialog.setTitle("결제완료");
        FinalDialog.setLocationRelativeTo(null);
        FinalDialog.setVisible(true);

        confirmBt.addActionListener(e -> clickedConfirm());
    }

    private void clickedConfirm(){
        FinalDialog.dispose();
        p.clearCartList();
        f.cardLayout.show(f.cardPanel, "LoginPanel");
    }
}
