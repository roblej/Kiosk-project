package client.order;

import client.MainFrame;
import org.apache.ibatis.session.SqlSession;
import vo.CouponVO;

import javax.swing.*;
// --- 수정된 부분: 누락된 import 구문들 추가 ---
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
// --- 여기까지 수정 ---
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartPanel extends JPanel {

    private OrderPanel orderPanel;
    private List<String[]> cartList;
    JTable table;
    JScrollPane scrollPane;
    MainFrame f;
    JLabel bottomLabel;
    int allPrice;

    int i = 100;

    JButton backBtn;
    JButton delBtn;
    JButton payBtn;

    String[] pvo_name = {"주문상품", "옵션", "주문수량", "주문가격"};
    String[][] data;

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

        table.setBackground(Color.WHITE);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setRowHeight(25);

        // --- 셀 내용 및 헤더 가운데 정렬 ---
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < table.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(centerRenderer);
        }

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        if (headerRenderer != null) {
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        }

        JPanel bottomLarea = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel bottomRarea = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2));

        bottomLarea.setBackground(Color.WHITE);
        bottomRarea.setBackground(Color.WHITE);

        bottomPanel.add(bottomLarea, BorderLayout.WEST);
        bottomPanel.add(bottomRarea, BorderLayout.EAST);

        bottomLarea.add(bottomLabel = new JLabel("총 금액: 0원"), BorderLayout.WEST);

        backBtn = new JButton("첫화면");
        delBtn = new JButton("지우기");
        payBtn = new JButton("결제");

        bottomRarea.add(backBtn);
        bottomRarea.add(delBtn);
        bottomRarea.add(payBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- 리스너 추가 ---
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "첫 화면으로 이동하시겠습니까?","", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    if (cartList != null && !cartList.isEmpty()){
                        clearCartList();
                    }
                    f.cardLayout.show(f.cardPanel, "LoginPanel");
                }
            }
        });

        delBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                if (i >= 0 && i < model.getRowCount()) {
                    cartList.remove(i);
                    i = 100;
                    calTotalPrice();
                    updateTable();
                } else {
                    JOptionPane.showMessageDialog(null, "지울 항목을 선택해주세요");
                }
            }
        });

        payBtn.addActionListener(e -> cliked_Payment(f));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 테이블에서 더블클릭을 알아내자!
                int cnt = e.getClickCount();
                if(cnt == 1){
                    // JTable에 선택된 행, index를 얻어내자
                    i = table.getSelectedRow();
                }
            }
        });

        setVisible(true);
    } // 생성자 끝

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

        // 테이블 모델 변경 후에도 정렬이 유지되도록 정렬 로직을 다시 적용합니다.
        alignTableContentsCenter();
    }

    // 정렬 로직을 별도의 메소드로 분리하여 재사용성을 높입니다.
    private void alignTableContentsCenter() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        TableColumnModel columnModel = table.getColumnModel();
        for (int i = 0; i < table.getColumnCount(); i++) {
            columnModel.getColumn(i).setCellRenderer(centerRenderer);
        }

        DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
        if (headerRenderer != null) {
            headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        }
    }

    public void clearCartList() {
        cartList.clear();
        allPrice = 0;
        updatePrice(allPrice);
        updateTable();
    }

    public void updatePrice(int allPrice) {
        this.allPrice = allPrice;
        bottomLabel.setText("총 금액: " + allPrice + "원");
    }

    public void cliked_Payment(MainFrame f) {
        if (!cartList.isEmpty()) {
            int cnt = JOptionPane.showConfirmDialog(null, "쿠폰을 사용하시겠습니까?", "", JOptionPane.YES_NO_OPTION);
            if (cnt == 0) {
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
                    new CouponDialog(f, cvo, orderPanel, CartPanel.this);
                } else {
                    //쿠폰코드가 사용할 수 없을 경우
                    JOptionPane.showMessageDialog(null, "사용할 수 없는 쿠폰코드입니다");
                }
            } else {
                //NO를 선택할 경우 결제화면으로 넘어감
                new CouponDialog(f, orderPanel, CartPanel.this);
            }
        }else { // 장바구니에 품목이 없다면
                JOptionPane.showMessageDialog(null, "상품을 담아주세요");
            }
    }

    public void calTotalPrice(){
        // 총 금액계산
        int total = 0;
        for (String[] row : cartList) {
            total += Integer.parseInt(row[3]);
        }
        updatePrice(total);
    }
}