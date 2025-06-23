package client.admin;

import vo.order_VO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StatusChange extends JDialog {

    JButton pending, process, finish;
    MyDialog parent;
    order_VO vo;

    public StatusChange(MyDialog parent, boolean modal, order_VO vo) {
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

        // 창 설정
        this.setSize(400, 100);
        this.setLocationRelativeTo(null); // 화면 가운데

    }


}
