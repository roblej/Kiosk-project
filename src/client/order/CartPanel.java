package client.order; // 패키지 변경

import client.MainFrame;
import org.apache.ibatis.jdbc.Null;
import org.apache.ibatis.session.SqlSession;
import vo.CouponVO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
이곳은 OptionDialog에서 저장된 값의 정보가 담길 class이다
OptionDialog에서 전달하고자 하는 이름, 가격을 멤버변수로 만들어야하고
이곳의 결제하기 버튼을 눌렀을 때 CartPanel의 List는 초기화 되어야하며 쿠폰 사용 유무 창을 띄워야한다.
 */

public class CartPanel extends JPanel {

    private OrderPanel orderPanel; // MainFrame 대신 OrderPanel을 참조
    private List<String[]> cartList;
    JTable table;
    JScrollPane scrollPane;
    MainFrame f;
    JLabel bottomLabel;
    int allPrice;

    int i = 100;

    JButton backBtn; // 첫 화면으로
    JButton delBtn; // 장바구니 품목 지우기
    JButton payBtn; // 결제벝

    String[] pvo_name = {"주문상품", "주문수량", "주문가격", "옵션"};
    String[][] data;

    // 생성자에서 MainFrame 대신 OrderPanel을 받도록 수정
    public CartPanel(MainFrame f, OrderPanel orderPanel, List<String[]> cartList) {
        this.f = f;
        this.orderPanel = orderPanel;
        this.cartList = cartList;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(500, 220));
        setBackground(Color.WHITE);
        add(new JLabel("장바구니", SwingConstants.CENTER), BorderLayout.NORTH);

        table = new JTable(new DefaultTableModel(data, pvo_name) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        table.setBackground(Color.WHITE);           // 배경 흰색
        table.setShowGrid(false);                   // 선 없음
        table.setIntercellSpacing(new Dimension(0, 0)); // 셀 간격 제거
        table.setRowHeight(25);

        // List -> 2차원 배열 변환
        data = new String[cartList.size()][pvo_name.length];
        for (int j = 0; j < cartList.size(); j++) {
            data[j] = cartList.get(j);
        }

        JPanel bottomLarea = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel bottomRarea = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));

        bottomLarea.setBackground(Color.WHITE);
        bottomRarea.setBackground(Color.WHITE);

        bottomPanel.add(bottomLarea, BorderLayout.WEST);
        bottomPanel.add(bottomRarea, BorderLayout.EAST);


        bottomLarea.add(bottomLabel = new JLabel("총 금액: " + allPrice + "원"), BorderLayout.WEST); // 담은게 없어도 가격 보이게 하기

        backBtn = new JButton("첫화면");
        delBtn = new JButton("지우기");
        payBtn = new JButton("결제");

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "첫 화면으로 이동하시겠습니까?","", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    if(data != null && data.length != 0){
                        data = null;
                        f.cardLayout.show(f.cardPanel, "LoginPanel");
                        clearCartList();
                    }else{
                        f.cardLayout.show(f.cardPanel, "LoginPanel");
                    }
                } else if (result == JOptionPane.NO_OPTION) {
                    // 아무 동작도 하지 않으면 창만 그대로 유지됨 (닫히지 않음)
                }
            }
        });

        // 장바구니 삭제 버튼 이벤트 감지자
        delBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                if (i >= 0 && i < model.getRowCount()) { // 사용자가 table의 행을 선택했을 때만 수행
                    cartList.remove(i); // 장바구니의 i번째 리스트 제거
                    i = 100; // 사용자 선택 값 초기화

                    calTotalPrice(); // 총 금액 갱신
                    updateTable(); // 테이블 갱신
                }else{
                    JOptionPane.showMessageDialog(null, "지울 항목을 선택해주세요");
                }
                i = 100;
            }
        });

        payBtn.addActionListener(e -> cliked_Payment(f));

        bottomRarea.add(backBtn);
        bottomRarea.add(delBtn);
        bottomRarea.add(payBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 테이블에서 더블클릭을 알아내자!
                int cnt = e.getClickCount();
                if(cnt == 1){
                    // JTable에 선택된 행, index를 얻어내자
                    i = table.getSelectedRow();
                    System.out.println(i);
                }
            }
        });

        setVisible(true);
    } // 생자 끝

    // 테이블 내부의 값 반복문 수행하면서 확인 후 화면에 보여줌
    public void updateTable() {
        data = new String[cartList.size()][pvo_name.length];
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

    // 장바구니 내용 비우는 함수
    public void clearCartList(){
        cartList.clear(); // 장바구니 초기화
        allPrice = 0;
        updatePrice(allPrice); // 총 가격 초기화
        updateTable(); // 초기화 한 장바구니 보여주기
    }

    // 총 금액 알아내는 함수
    public void updatePrice(int allPrice){
        this.allPrice = allPrice; // 멤버변수의 allPrice에 값 넣어줌
        bottomLabel.setText("총 금액: " + allPrice + "원");
    }

    public void cliked_Payment(MainFrame f){
        if(data.length != 0) { // 장바구니의 품목이 있을때만
            int cnt = JOptionPane.showConfirmDialog(null, "쿠폰을 사용하시겠습니까?", "", JOptionPane.YES_NO_OPTION);
            if (cnt == 0) {
                //YES를 선택할 경우 쿠폰 사용 화면으로 넘어감
                String coupon_Code = JOptionPane.showInputDialog(null, "코드를 입력하세요", null);
                CouponVO cvo;
                Map<String, String> map = new HashMap();
                map.put("c_code", coupon_Code);
                SqlSession ss = f.factory.openSession();
                cvo = ss.selectOne("coupon.couponConfirm", map);

                ss.close();
                if (cvo != null && coupon_Code.equals(cvo.getC_code())) {
                    //쿠폰코드가 사용할 수 있는 경우
                    JOptionPane.showMessageDialog(null, "쿠폰이 확인되었습니다");
                    CouponDialog CD = new CouponDialog(f, cvo, orderPanel);
                } else {
                    //쿠폰코드가 사용할 수 없을 경우
                    JOptionPane.showMessageDialog(null, "사용할 수 없는 쿠폰코드입니다");
                }
            } else {
                //NO를 선택할 경우 결제화면으로 넘어감
                f.cardLayout.show(f.cardPanel, "FinalPayment");
            }
        }else { // 장바구니에 품목이 없다면
                JOptionPane.showMessageDialog(null, "상품을 담아주세요");
            }
    }

    public void calTotalPrice(){
        // 총 금액계산
        int total = 0;
        for (String[] row : cartList) {
            total += Integer.parseInt(row[2]); // 주문가격은 문자열로 들어있으니 정수로 변환
        }
        updatePrice(total);
    }
}