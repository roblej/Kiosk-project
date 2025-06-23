package client.order; // 패키지 변경

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

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

    JLabel bottomLabel;
    int allPrice;

    String[] pvo_name = {"주문상품", "주문수량", "주문가격", "옵션"};

    // 생성자에서 MainFrame 대신 OrderPanel을 받도록 수정
    public CartPanel(OrderPanel orderPanel, List<String[]> cartList) {
        this.orderPanel = orderPanel;
        this.cartList = cartList;
        this.allPrice = allPrice;

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

        int totalPrice = calcTotal(cartList);
        JPanel bottomPanel = new JPanel(new BorderLayout());

        bottomPanel.add(bottomLabel = new JLabel("총 금액: " + allPrice + "원"), BorderLayout.CENTER);

        JButton payBtn = new JButton("결제하기");
        payBtn.addActionListener(e -> {
            cartList.clear(); // 장바구니 초기화
            JOptionPane.showMessageDialog(this, "쿠폰을 사용하시겠습니까?");
            // 테이블 새로 고침 필요 (다시 생성 or 테이블 모델 초기화)
        });

        bottomPanel.add(payBtn, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private int calcTotal(List<String[]> list) {
        int total = 0;
        for (String[] row : list) {
            total += Integer.parseInt(row[2]); // 주문가격
        }
        return total;
    }

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

    public void updatePrice(int allPrice){
        this.allPrice = allPrice; // 멤버변수의 allPrice에 값 넣어줌
        bottomLabel.setText("총 금액: " + allPrice + "원");
    }
}