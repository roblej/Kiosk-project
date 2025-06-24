package client.order;

import client.MainFrame;
import vo.CouponVO;

import javax.swing.*;
import java.awt.*;

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

    public CouponDialog(MainFrame f, CouponVO cvo, OrderPanel orderPanel){
        //총 주문금액, 쿠폰 할인율, 최종 결제금액
        this.f = f;
        this.cvo = cvo;
        this.orderPanel = orderPanel;

        Dialog = new JDialog();
        north_p = new JPanel();
        center_p = new JPanel();
        south_p = new JPanel();

        totalPrice = orderPanel.returnPrice;
        String totalPrice2 = String.format("%,d",totalPrice);
        north_p.add(new JLabel("총 주문금액 : " + totalPrice2));

        String ratePrice = cvo.getC_discount_rate();
        int cnt = (int) (totalPrice * Double.parseDouble(ratePrice)*0.01);
        finalPrice = String.format("%,d",cnt);
        center_p.add(new JLabel("쿠폰적용 후 최종 결제 금액 : "+ finalPrice));

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

    private void clicked_confirm(){
        //최종 결제금액 확인되어 결제창으로 넘어감
//        FinalPayment FP = new FinalPayment();
        returnFinalprice(finalPrice);
        f.cardLayout.show(f.cardPanel, "FinalPayment");
        Dialog.dispose();
    }

    private void clicked_cancel(){
        Dialog.dispose();
    }

    private void returnFinalprice(String finalPrice){

    }
}
