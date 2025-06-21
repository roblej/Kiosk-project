package client.Closing_sales;

import client.MainFrame;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import vo.OrderVO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ClosingSalesPanel extends JPanel {

    JToggleButton today_btn, month_btn, period_btn;
    JPanel north_p, search_p;
    JTable table;
    String[] item = {"주문번호", "가격", "수량", "할인", "총금액"};
    String[][] data;

    JComboBox<String> monthbox, daybox;

    SqlSessionFactory factory;
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

        this.add(new JScrollPane(table = new JTable()));
        table.setModel(new DefaultTableModel(data, item));


        this.setBounds(500, 150, 500, 800);
        this.setVisible(true);


        today_btn.addActionListener(e -> today_paymentAmount());

        month_btn.addActionListener(e -> month_paymentAmount());

        period_btn.addActionListener(e -> period_paymentAmount());

//        this.addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent e) {
//                System.exit(0);
//            }
//        });

    }

    private void init() {
//        try {
//            Reader r = Resources.getResourceAsReader("client/config/conf.xml");
//            factory = new SqlSessionFactoryBuilder().build(r);
//            ss = factory.openSession();
//            r.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        ss = f.factory.openSession();
    }

    private void viewtable(List<OrderVO> list) {
        data = new String[list.size()][item.length];
        int i = 0;
        for (OrderVO vo : list) {
            data[i][0] = vo.getO_number();
            data[i][1] = vo.getOiv().getOi_price();
            data[i][2] = vo.getOiv().getOi_quantity();
            data[i][3] = vo.getOiv().getOptions(); //할인??
            data[i][4] = vo.getO_total_amount();
            i++;
        }
        table.setModel(new DefaultTableModel(data, item));
    }

    private void today_paymentAmount() {
        init();
        List<OrderVO> list = ss.selectList("paymentAmount.today");
        viewtable(list);
        ss.close();
    }

    private void month_paymentAmount() {
        init();
        List<OrderVO> list = ss.selectList("paymentAmount.month");
        viewtable(list);
        ss.close();
    }

    private void period_paymentAmount() {
        search_p = new JPanel();
        monthbox = new JComboBox<>();
        daybox = new JComboBox<>();
        JButton con = new JButton("확인");
        JButton can = new JButton("취소");

        //월 입력박스
        for (int i = 1; i <= 12; i++) {
            monthbox.addItem(i + "월");
        }

        //일 입력박스
        for (int j = 1; j <= 31; j++) {
            daybox.addItem(j + "일");
        }

        search_p.add(new JLabel("월 :"));
        search_p.add(monthbox);
        search_p.add(new JLabel("일 :"));
        search_p.add(daybox);
        search_p.add(con, BorderLayout.SOUTH);
        search_p.add(can, BorderLayout.SOUTH);


        JDialog dialog = new JDialog();
        dialog.setSize(400, 150);
        dialog.add(search_p);
        dialog.setVisible(true);

        con.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        can.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


//            int res = JOptionPane.showConfirmDialog(null,
//                "날짜를 선택하세요","날짜 선택",
//                    JOptionPane.OK_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE);
//
//            if(res==JOptionPane.OK_OPTION){
//                int selectedMonth = monthbox.getSelectedIndex()+1;
//                int selectedDay = daybox.getSelectedIndex()+1;
//
//
//                String selectedDate = selectedMonth + "월 " + selectedDay + "일";
//                JOptionPane.showMessageDialog(null, "선택한 날짜: " + selectedDate);
//
//            }else {
//                JOptionPane.showMessageDialog(null,"취소되었습니다.");
//            }


//        init();
//        List<order_VO> list = ss.selectList("paymentAmount.period");
//        viewtable(list);
//        ss.close();
    }
}
