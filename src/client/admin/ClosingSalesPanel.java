package client.admin;

import client.MainFrame;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import vo.order_VO;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.*;
import java.util.List;


public class ClosingSalesPanel extends JPanel {

    JToggleButton today_btn, month_btn, period_btn;
    JButton back_btn;
    JPanel north_p,south_p;
    JTable table;
    String[] item = {"주문번호", "가격", "수량", "할인", "총금액","상태"};
    String[][] data;

//    SqlSessionFactory factory;
    SqlSession ss;
    MainFrame f;

    public ClosingSalesPanel(MainFrame f) {
        this.f = f;
        north_p = new JPanel();

        today_btn = new JToggleButton("일정산");
        month_btn = new JToggleButton("월정산");
        period_btn = new JToggleButton("기간별 정산");

        ButtonGroup group = new ButtonGroup();
        group.add(today_btn);
        group.add(month_btn);
        group.add(period_btn);

        north_p.add(today_btn);
        north_p.add(month_btn);
        north_p.add(period_btn);
        this.add(north_p, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel();

        centerPanel.add(new JScrollPane(table = new JTable()));
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {{
            setHorizontalAlignment(SwingConstants.CENTER); //DefaultRenderer를 생성하여 모든 셀에 적용
        }});
        table.setPreferredScrollableViewportSize(new Dimension(500,550)); //셀 안에 데이터를 가운데 정렬
        table.setModel(new DefaultTableModel(data, item));
        this.add(centerPanel,BorderLayout.CENTER);

        south_p = new JPanel();
        south_p.add(back_btn = new JButton("뒤로가기"));
        this.add(south_p, BorderLayout.SOUTH);

        this.setBounds(500, 150, 500, 800);
        this.setVisible(true);


        today_btn.addActionListener(e -> today_paymentAmount());

        month_btn.addActionListener(e -> month_paymentAmount());

        period_btn.addActionListener(e -> period_paymentAmount());

        back_btn.addActionListener(e -> back_Action());

    }

    private void init() {
        ss = f.factory.openSession();
    }

    private void viewtable(List<order_VO> list) {
        data = new String[list.size()+1][item.length];
        int i = 0;
        int total_price = 0;
        int total_amount = 0;
        int total_quantity = 0;
        int total_coupon_sale = 0;
        java.util.Set<String> processedOrderNumbers = new java.util.HashSet<>();

        for (order_VO vo : list) {
                data[i][0] = vo.getO_number();
                int price1 = Integer.parseInt(vo.getOi_price());
                data[i][1] = String.format("%,d", price1);
                data[i][2] = vo.getOi_quantity();

                int amount1 = Integer.parseInt(vo.getO_total_amount());
                String currentOrderNumber = vo.getO_number();

                if (processedOrderNumbers.contains(currentOrderNumber)) {
                    data[i][4] = "";
                    data[i][3] = "";
                } else {
                    String couponSaleStr = vo.getO_coupon_sale();
// 쿠폰 할인액이 null이 아니고, 비어있는 문자열이 아닐 경우에만 숫자로 변환하여 더합니다.
                    if (couponSaleStr != null && !couponSaleStr.trim().isEmpty()) {
                        try {
                            total_coupon_sale += Integer.parseInt(couponSaleStr);
                            data[i][3] = vo.getO_coupon_sale();
                        } catch (NumberFormatException e) {
                            // 혹시 모를 다른 예외 상황 (예: 문자열이 "abc"인 경우)에 대비
                            System.err.println("쿠폰 할인액 숫자 변환 오류: " + couponSaleStr);
                        }
                    }
                    data[i][4] = String.format("%,d", amount1);
                    processedOrderNumbers.add(currentOrderNumber);
                    total_amount += amount1;
                }
                data[i][5] = vo.getO_status();
                total_price += price1;
                total_quantity += Integer.parseInt(data[i][2]);
                i++;
        }//for문의 끝
        data[list.size()][1] = String.format("%,d",total_price);
        data[list.size()][2] = String.format("%,d",total_quantity);
        data[list.size()][3] = String.format("%,d",total_coupon_sale);
        data[list.size()][4] = String.format("%,d",total_amount);
        table.setModel(new DefaultTableModel(data, item));
    }//view 끝

    private void back_Action(){
        f.cardLayout.show(f.cardPanel, "AdminCard");
    }

    private void today_paymentAmount() {
        init();
        List<order_VO> list = ss.selectList("orders.today");
        viewtable(list);
        ss.close();
    }

    private void month_paymentAmount() {
        init();
        List<order_VO> list = ss.selectList("orders.month");
        viewtable(list);
        ss.close();
    }

    private void period_paymentAmount() {
        JPanel search_p = new JPanel();
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();

        JComboBox<String> start_yearBox = new JComboBox<>();
        JComboBox<String> start_monthBox = new JComboBox<>();
        JComboBox<String> start_dayBox = new JComboBox<>();
        JComboBox<String> end_yearBox = new JComboBox<>();
        JComboBox<String> end_monthBox = new JComboBox<>();
        JComboBox<String> end_dayBox = new JComboBox<>();

        JButton confirm_btn = new JButton("확인");
        JButton cancel_btn = new JButton("취소");

        //연도 입력박스
        int current_year = LocalDate.now().getYear();

        for (int year = current_year; year >= current_year - 10; year--){
            start_yearBox.addItem(String.valueOf(year));
            end_yearBox.addItem(String.valueOf(year));
        }

        //월 입력박스
        for (int i = 1; i <= 12; i++) {
            if(i<10){
                start_monthBox.addItem("0"+i);
                end_monthBox.addItem("0"+i);
            }
            else {
                start_monthBox.addItem(String.valueOf(i));
                end_monthBox.addItem(String.valueOf(i));
            }
        }

        //일 입력박스
        for (int j = 1; j <= 31; j++) {
            if(j<10) {
                start_dayBox.addItem("0"+j);
                end_dayBox.addItem("0"+j);
            }else {
                start_dayBox.addItem(String.valueOf(j));
                end_dayBox.addItem(String.valueOf(j));
            }
        }


        panel1.add(new JLabel("[시작일]"));
        panel1.add(new JLabel("년도 :"));
        panel1.add(start_yearBox);
        panel1.add(new JLabel("월 :"));
        panel1.add(start_monthBox);
        panel1.add(new JLabel("일 :"));
        panel1.add(start_dayBox);


        panel2.add(new JLabel("[종료일]"));
        panel2.add(new JLabel("년도 :"));
        panel2.add(end_yearBox);
        panel2.add(new JLabel("월 :"));
        panel2.add(end_monthBox);
        panel2.add(new JLabel("일 :"));
        panel2.add(end_dayBox);

        panel3.add(confirm_btn);
        panel3.add(cancel_btn);

        search_p.add(panel1, BorderLayout.NORTH);
        search_p.add(panel2);
        search_p.add(panel3, BorderLayout.SOUTH);

        JDialog dialog = new JDialog();
        dialog.setSize(400, 180);
        dialog.add(search_p);
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(null);

        confirm_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String selected_syear = (String) start_yearBox.getSelectedItem(); //반환형 > object여서 캐스팅
                String selected_smonth = (String) start_monthBox.getSelectedItem();
                String selected_sday = (String) start_dayBox.getSelectedItem();
                String selected_eyear = (String) end_yearBox.getSelectedItem();
                String selected_emonth = (String) end_monthBox.getSelectedItem();
                String selected_eday = (String) end_dayBox.getSelectedItem();



                StringBuffer start_date = new StringBuffer();
                start_date.append(selected_syear.substring(2,4));
                start_date.append(selected_smonth);
                start_date.append(selected_sday);


                StringBuffer end_date = new StringBuffer();
                end_date.append(selected_eyear.substring(2,4));
                end_date.append(selected_emonth);
                end_date.append(selected_eday);


                int syear = Integer.parseInt(start_date.toString());
                int eyear = Integer.parseInt(end_date.toString());

                if(syear<eyear){
                Map<String,String> map = new HashMap<>();
                order_VO vo = new order_VO();
                map.put("start_date",start_date.toString());
                map.put("end_date",end_date.toString());
                map.put("o_number",vo.getO_number());
                map.put("o_total_amount",vo.getO_total_amount());
                map.put("oi_quantity",vo.getOi_quantity());
                map.put("oi_price",vo.getOi_price());
                map.put("o_status",vo.getO_status());

                init();

                List<order_VO> list = ss.selectList("orders.period",map);
                viewtable(list);
                ss.close();
                }else {
                    JOptionPane.showMessageDialog(null,"날짜를 확인해주세요.");
                }

                dialog.dispose();
            }
        });

        cancel_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
    }
}
