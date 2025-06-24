package client.order;

import client.MainFrame;
import org.apache.ibatis.session.SqlSession;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FinalPayment extends JPanel {

    MainFrame f;
    JPanel north_p, south_p;
    JButton confirmBt;


    public FinalPayment(MainFrame f){
        this.f = f;

        north_p = new JPanel();
        south_p = new JPanel();
        confirmBt = new JButton("확인");

        north_p.add(new JLabel("결제가 완료되었습니다"));
        south_p.setLayout(new FlowLayout(FlowLayout.CENTER));
        south_p.add(confirmBt);

        this.setLayout(new BorderLayout());
        this.add(north_p,BorderLayout.CENTER);
        this.add(south_p,BorderLayout.SOUTH);

        confirmBt.addActionListener(e -> clikedConfirm());
    }

    public void clikedConfirm(){
        //o_idx, o_number, o_total_amount, o_status, o_is_takeout, user_id

        Map<String,String> map = new HashMap<>();
        DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyMMdd");
        String nowDate = LocalDate.now().format(DTF);
        int n = (int)(Math.random()*1000+0);
        map.put("o_number",nowDate + n);
//        map.put("o_total_amount",)

        f.cardLayout.show(f.cardPanel, "LoginPanel");
    }
}
