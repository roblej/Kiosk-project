package client.order; // 패키지 변경

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

    int i = 100;

    JButton delBtn; // 장바구니 품목 지우기

    String[] pvo_name = {"주문상품", "주문수량", "주문가격", "옵션"};
    String[][] data;

    // 생성자에서 MainFrame 대신 OrderPanel을 받도록 수정
    public CartPanel(OrderPanel orderPanel, List<String[]> cartList) {
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

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(bottomLabel = new JLabel("총 금액: " + allPrice + "원"), BorderLayout.CENTER); // 담은게 없어도 가격 보이게 하기

        delBtn = new JButton("지우기");
        JButton payBtn = new JButton("결제하기");

        // 장바구니 삭제 버튼 이벤트 감지자
        delBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                if (i >= 0 && i < model.getRowCount()) {
                    model.removeRow(i); // i번째 행 삭제
                }
                i = 100;
            }
        });

        payBtn.addActionListener(e -> {
            clearCartList();
            JOptionPane.showMessageDialog(this, "결제하시겠습니까?");
            // 테이블 새로 고침 필요 (다시 생성 or 테이블 모델 초기화)
        });

        bottomPanel.add(delBtn);
        bottomPanel.add(payBtn);
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
    }

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
}