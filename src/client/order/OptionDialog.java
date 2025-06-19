package client.order;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OptionDialog extends JDialog {



    public OptionDialog() {

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

    }

    private void initComponents() {

        JButton shortBtn, tallBtn, ventiBtn;
        JButton hotBtn,iceBtn;
        JPanel east_p,center_p, south_p;

        setBounds(300, 300, 400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setVisible(true);
    }

    public static void main(String[] args) {
        new OptionDialog().setVisible(true);
    }

}
