package client.admin;

import client.admin.MyDialog;
import vo.OrdersVO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Status_Change extends JDialog {

    JButton pending, process, finish;
    MyDialog parent;
    OrdersVO vo;

    public Status_Change(MyDialog parent, boolean modal, OrdersVO vo) {
        super(parent, modal);
        this.parent = parent;
        this.vo = vo; // 저장

        initComponents();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // 이벤트 감지자 등록
        pending.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("test");
                vo.setO_status("조리중");
                parent.updateData(vo);
                dispose();
            }
        });

        process.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vo.setO_status("처리완료");
                parent.updateData(vo);
                dispose();
            }
        });

        finish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vo.setO_status("취소");
                parent.updateData(vo);
                dispose();
            }
        });
        this.setVisible(true);
    }

    private void initComponents(){
        JPanel center_p;
        center_p = new JPanel();
        center_p.setLayout(new GridLayout(1, 3));
        center_p.add(pending = new JButton("조리중"));
        center_p.add(process = new JButton("처리완료"));
        center_p.add(finish = new JButton("취소"));
        this.add(center_p, BorderLayout.CENTER);

        // ✅ 창 설정
        this.setSize(400, 100);
        this.setLocationRelativeTo(null); // 화면 가운데

    }


}
