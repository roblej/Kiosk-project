package client.order; // 패키지 변경

import client.MainFrame;
import org.apache.ibatis.session.SqlSession;
import vo.CouponVO;
import vo.ProductsVO;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.border.MatteBorder;

/*
이곳은 OptionDialog에서 저장된 값의 정보가 담길 class이다
OptionDialog에서 전달하고자 하는 이름, 가격을 멤버변수로 만들어야하고
이곳의 결제하기 버튼을 눌렀을 때 CartPanel의 List는 초기화 되어야하며 쿠폰 사용 유무 창을 띄워야한다.
 */

public class CartPanel extends JPanel {

    private OrderPanel orderPanel; // MainFrame 대신 OrderPanel을 참조
    List<ProductsVO> productsList;
    OptionDialog d;
    ProductsVO p;
    List<CouponVO> couponList;
    MainFrame f;
    JButton Payment_Btn;
    SqlSession ss;
    CouponPanel CouponPanel;
    // 생성자에서 MainFrame 대신 OrderPanel을 받도록 수정
    public CartPanel(MainFrame f,OrderPanel orderPanel, ProductsVO vo, int totalPrice) {

        this.orderPanel = orderPanel;
        this.p = vo;

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500, 220));
        setBackground(Color.WHITE);
        setBorder(new MatteBorder(1, 0, 0, 0, new Color(220, 220, 220)));

        // (이하 장바구니 UI 구성 요소... - 이전 코드와 유사)
        add(new JLabel("장바구니: " + vo.getP_name(), SwingConstants.CENTER), BorderLayout.NORTH);

//        productsList = new ArrayList<>();
//        productsList.add(vo);


        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(new JLabel("총 금액: " + totalPrice + "원"), BorderLayout.CENTER);
        bottomPanel.add(Payment_Btn = new JButton("결제하기"), BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);


        Payment_Btn.addActionListener(e -> cliked_Payment(f));
    }

    public void cliked_Payment(MainFrame f){


        int cnt = JOptionPane.showConfirmDialog
                (null,"쿠폰을 사용하시겠습니까?","",JOptionPane.YES_NO_OPTION);
        if(cnt==0){
            //YES를 선택할 경우 쿠폰 사용 화면으로 넘어감
            String coupon_Code = JOptionPane.showInputDialog(null,"코드를 입력하세요",null);
            CouponVO cvo;
            Map<String,String> map = new HashMap();
            map.put("c_code",coupon_Code);
            ss = f.factory.openSession();
            cvo = ss.selectOne("coupon.couponConfirm",map);

            if (cvo != null && coupon_Code.equals(cvo.getC_code())){
                //쿠폰코드가 사용할 수 있는 경우
                JOptionPane.showMessageDialog(null,"쿠폰이 확인되었습니다");
                f.cardLayout.show(f.cardPanel,"CouponPanel");
            }else {
                //쿠폰코드가 사용할 수 없을 경우
                JOptionPane.showMessageDialog(null,"사용할 수 없는 쿠폰코드입니다");
            }
        }else {
            //NO를 선택할 경우 결제화면으로 넘어감
            f.cardLayout.show(f.cardPanel,"CouponPanel");
        }
        //ss.close();

    }
        public static void main(String[] args) {
        MainFrame f = new MainFrame();
        f.setVisible(false);
        new CartPanel(f,null, null, 0);
    }
        }

