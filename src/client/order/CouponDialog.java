package client.order;

import client.MainFrame;
import org.apache.ibatis.session.SqlSession;
import vo.CouponVO;
import vo.ProductsVO;
import vo.order_VO;
import vo.order_items_VO;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

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
    List<String[]> cartlist;
    List<order_VO> o_list;
    List<order_items_VO> oi_list;

    // 쿠폰을 적용했을 때 생성하는 생성자
    public CouponDialog(MainFrame f, CouponVO cvo, OrderPanel orderPanel, CartPanel p){
        //총 주문금액, 쿠폰 할인율, 최종 결제금액
        this.f = f;
        this.cvo = cvo;
        this.orderPanel = orderPanel;
        this.p = p;
        this.cartlist = p.cartList;
        Dialog = new JDialog();
        north_p = new JPanel();
        center_p = new JPanel();
        south_p = new JPanel();

        totalPrice = orderPanel.returnPrice;
        String totalPrice2 = String.format("%,d",totalPrice);
        north_p.add(new JLabel("총 주문금액 : " + p.allPrice));

        String ratePrice = cvo.getC_discount_rate();
        int cnt = totalPrice - ((int)(totalPrice * Double.parseDouble(ratePrice)*0.01));
        finalPrice = String.format("%,d이게 받는 결제금액인가?",cnt);

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

        confirmBt.addActionListener(e -> clicked_confirm()); // 확인버튼 눌렀을 시 액션을 감지해서 클릭드_컨펌 이라는 메소드 호출함

        cancelBt.addActionListener(e -> clicked_cancel()); // 취소버튼 눌렀을 시 액션을 감지해서 클릭드_캔슬 이라는 메소드 호출함
    }//생성자의 끝
    
    // 쿠폰을 적용하지 않았을 때 생성하는 생성자
    public CouponDialog(MainFrame f, OrderPanel orderPanel, CartPanel p){
        //총 주문금액, 쿠폰 할인율, 최종 결제금액
        this.f = f;
        this.orderPanel = orderPanel;
        this.p = p;
        this.cartlist = p.cartList;
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
        //order(주문)테이블에 사용자 정보 추가
        Map<String,String> map = new HashMap<>();
        DateTimeFormatter DTF = DateTimeFormatter.ofPattern("yyMMdd");
        String nowDate = LocalDate.now().format(DTF); //현재날짜를 형식에 맞게 변환 후 변수에 저장
        int n = (int)(Math.random()*1000+0);
        String orderNumber = nowDate + n;
        map.put("o_number",orderNumber);
        map.put("o_total_amount", String.valueOf(totalPrice));
        map.put("user_id", MainFrame.userId);
        map.put("o_is_takeout", String.valueOf(MainFrame.orderType));
        map.put("o_status","조리중");
        SqlSession ss = f.factory.openSession();
        int cnt = ss.insert("orders.addorders",map);
        if(cnt>0){
            ss.commit();
            for (int i=0;i<cartlist.size();i++) {
                Map<String,String> map2 = new HashMap<>();

                String[] currentItem = cartlist.get(i);
                map2.put("oi_size",currentItem[1]);//사이즈
                map2.put("oi_quantity",currentItem[2]);//수량
                map2.put("oi_price",currentItem[3]);//가격
                map2.put("product_code",currentItem[4]);//코드
                map2.put("oi_id", orderNumber); //orders테이블의 o_number
                int cnt2 = ss.insert("order_items.addorder_items",map2);
                if(cnt2>0){
                    ss.commit();
                    Map<String,String> map3 = new HashMap<>();
                    //상품 코드, 사이즈, 수량
                    //상품코드에 해당하는 아이템의 수량을 사이즈*수량으로 마이너스 small 1 venti 2 large 3
                    map3.put("p_code",currentItem[4]);
                    int a = Integer.parseInt(currentItem[2]);
                    int b;
                    switch (currentItem[1]){
                        case "short":
                            b = 1;
                            break;
                        case "Tall":
                            b = 2;
                            break;
                        case "Venti":
                            b = 3;
                            break;
                        default:
                            b = 1;
                    }

                    map3.put("p_stock",String.valueOf(a*b));
                    int cnt3 = ss.update("products.stockUpdate",map3);
                    // p_code조건으로 db의 p_stock을 map의 p_stock만큼 --
                    if(cnt3>0){
                        ss.commit();
                    }else
                        ss.rollback();

                }else {
                    ss.rollback();
                    System.out.println("커밋실패");
                }

            }
        }else {
            ss.rollback();
            System.out.println("커밋실패");
        }
        //oi_idx, oi_id, product_code, oi_quantity, oi_price, oi_size, options
        //order_items(주문상품)테이블에 주문에 대한 정보 추가



        ss.close();
        new FinalDialog(f, p);
        Dialog.dispose();

    }

    private void clicked_cancel(){
        Dialog.dispose();
    }
//
//    private void returnFinalprice(String finalPrice, FinalPayment FP){
////        FP.setFinalPayment(finalPrice, FP);
//    }
}
