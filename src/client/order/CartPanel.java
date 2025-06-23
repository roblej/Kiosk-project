package client.order; // 패키지 변경

import client.MainFrame;
import org.apache.ibatis.session.SqlSession;
import vo.CouponVO;
import vo.ProductsVO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private List<String[]> cartList;
    JTable table;
    JScrollPane scrollPane;

    JLabel bottomLabel;
    int allPrice;

    String[] pvo_name = {"주문상품", "주문수량", "주문가격", "삭제"};

    // 생성자에서 MainFrame 대신 OrderPanel을 받도록 수정
    public CartPanel(OrderPanel orderPanel, List<String[]> cartList) {
  //public CartPanel(MainFrame f,OrderPanel orderPanel, ProductsVO vo, int totalPrice) {
        this.orderPanel = orderPanel;
        this.cartList = cartList;

        // List -> 2차원 배열 변환
        String[][] data = new String[cartList.size()][pvo_name.length];
        for (int i = 0; i < cartList.size(); i++) {
            data[i] = cartList.get(i);
        }

        table = new JTable(new DefaultTableModel(data, pvo_name) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500, 220));
        add(new JLabel("장바구니", SwingConstants.CENTER), BorderLayout.NORTH);

        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(bottomLabel = new JLabel("총 금액: " + allPrice + "원"), BorderLayout.CENTER); // 담은게 없어도 가격 보이게 하기

        JButton payBtn = new JButton("결제하기");
        payBtn.addActionListener(e -> {
            clearCartList();
            JOptionPane.showMessageDialog(this, "결제하시겠습니까?");
            // 테이블 새로 고침 필요 (다시 생성 or 테이블 모델 초기화)
        });

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
  // 테이블 내부의 값 반복문 수행하면서 확인 후 화면에 보여줌
    public void updateTable() {
        String[][] data = new String[cartList.size()][pvo_name.length];
        for (int i = 0; i < cartList.size(); i++) {
            data[i] = cartList.get(i);
        }
        DefaultTableModel model = new DefaultTableModel(data, pvo_name) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setModel(model);
    }
        }

    // 장바구니 내용 비우는 함수
    public void clearCartList(){
        cartList.clear(); // 장바구니 초기화
        allPrice = 0;
        updatePrice(allPrice); // 총 가격 초기화
        updateTable(); // 초기화 한 장바구니 보여주기
    }

    public void updatePrice(int allPrice){
        this.allPrice = allPrice; // 멤버변수의 allPrice에 값 넣어줌
        bottomLabel.setText("총 금액: " + allPrice + "원");
    }
}
