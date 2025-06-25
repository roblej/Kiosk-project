package client.order;

import client.MainFrame;
import org.apache.ibatis.session.SqlSession;
import vo.CouponVO;
import vo.order_VO;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CouponDialog extends JDialog {
    //쿠폰 사용시 총 결제금액이 화면에 표시됨
    MainFrame f;
    CouponVO cvo;
    OrderPanel orderPanel;
    int totalPrice;
    String finalPrice;

    JDialog Dialog;
    JPanel north_p, center_p, south_p;
    JButton confirmBt, cancelBt;
    JLabel couponLabel;

    CartPanel p;


    List<order_VO> list;

    // 쿠폰을 적용했을 때 생성하는 생성자
    public CouponDialog(MainFrame f, CouponVO cvo, OrderPanel orderPanel, CartPanel p){
        //총 주문금액, 쿠폰 할인율, 최종 결제금액
        this.f = f;
        this.cvo = cvo;
        this.orderPanel = orderPanel;
        this.p = p;

        Dialog = new JDialog();
        north_p = new JPanel();
        center_p = new JPanel();
        south_p = new JPanel();

        totalPrice = orderPanel.returnPrice;
        String totalPrice2 = String.format("%,d",totalPrice);
        north_p.add(new JLabel("총 주문금액 : " + p.allPrice));

        String ratePrice = cvo.getC_discount_rate();
        int cnt = p.allPrice - ((int)(p.allPrice * Double.parseDouble(ratePrice)*0.01));
        finalPrice = String.format("%,d",cnt);

        center_p.add(couponLabel = new JLabel("쿠폰적용 후 최종 결제 금액 : "+ finalPrice));

        south_p.add(confirmBt = new JButton("확인"));
        south_p.add(cancelBt = new JButton("취소"));

        Dialog.add(north_p, BorderLayout.NORTH);
        Dialog.add(center_p,BorderLayout.CENTER);
        Dialog.add(south_p,BorderLayout.SOUTH);

        Dialog.setSize(500, 150);
        Dialog.setTitle("결제확인");
        Dialog.setLocationRelativeTo(null);
        Dialog.setVisible(true);

        confirmBt.addActionListener(e -> clicked_confirm());

        cancelBt.addActionListener(e -> clicked_cancel());
    }//생성자의 끝
    
    // 쿠폰을 적용하지 않았을 때 생성하는 생성자
    public CouponDialog(MainFrame f, OrderPanel orderPanel, CartPanel p){
        //총 주문금액, 쿠폰 할인율, 최종 결제금액
        this.f = f;
        this.orderPanel = orderPanel;
        this.p = p;

        Dialog = new JDialog();
        north_p = new JPanel();
        center_p = new JPanel();
        south_p = new JPanel();

        totalPrice = orderPanel.returnPrice;
        String totalPrice2 = String.format("%,d",totalPrice);
        north_p.add(new JLabel("총 주문금액 : " + p.allPrice));


        south_p.add(confirmBt = new JButton("확인"));
        south_p.add(cancelBt = new JButton("취소"));

        Dialog.add(north_p, BorderLayout.NORTH);
        Dialog.add(center_p,BorderLayout.CENTER);
        Dialog.add(south_p,BorderLayout.SOUTH);

        Dialog.setSize(500, 150);
        Dialog.setTitle("결제확인");
        Dialog.setLocationRelativeTo(null);
        Dialog.setVisible(true);

        confirmBt.addActionListener(e -> clicked_confirm());

        cancelBt.addActionListener(e -> clicked_cancel());
    }//생성자의 끝

    public CouponDialog(CartPanel cartPanel, MainFrame f, OrderPanel orderPanel) {
    }

    private void clicked_confirm(){
        //최종 결제금액 확인되어 결제창으로 넘어감
//        FinalPayment FP = new FinalPayment(f);
//        returnFinalprice(finalPrice, FP);
        //db에 보내는 로직 들어감
        //o_number, o_total_amount, user_id

        Map<String,String> map = new HashMap<>();
        DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyMMdd");
        String nowDate = LocalDate.now().format(DTF);
        int n = (int)(Math.random()*1000+0);
        map.put("o_number",nowDate + n);
        map.put("o_total_amount", String.valueOf(totalPrice));
        map.put("user_id", MainFrame.userId);
        map.put("o_is_takeout", String.valueOf(MainFrame.orderType));
        map.put("o_status","조리중");
        SqlSession ss = f.factory.openSession();
        List<order_VO> list = ss.selectList("orders.addorders",map);
        ss.close();
        new FinalDialog(f, p);
        Dialog.dispose();
    }

    private void clicked_cancel(){
        p.clearCartList();
        Dialog.dispose();
    }
//
//    private void returnFinalprice(String finalPrice, FinalPayment FP){
////        FP.setFinalPayment(finalPrice, FP);
//    }
}
