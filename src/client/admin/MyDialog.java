package client.admin;

import client.MainFrame;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import vo.order_VO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class MyDialog extends JDialog {

    List<order_VO> list;

    JTable table;
    JScrollPane scrollPane;

    int i;
    MainFrame f;

    public MyDialog(MainFrame f, boolean modal) {
        super(f, modal);
        this.f = f;

        list = getData(); // 값 채우기

        // 테이블 초기화 (빈 데이터라도 헤더 보이게 하기)
        table = new JTable(new DefaultTableModel(data, o_name) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        // 스크롤 가능하게 설정
        scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);

        // 창 설정
        this.setSize(600, 400);
        this.setLocationRelativeTo(null); // 화면 가운데

        
        // 이벤트 감지자 설정
        this.addWindowListener(new WindowAdapter() { // 창 x버튼 누를 때
            @Override
            public void windowClosing(WindowEvent e) {
                cnt = false;
                dispose();
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 테이블에서 더블클릭 알아내기
                int cnt = e.getClickCount();
                if(cnt == 2){
                    i = table.getSelectedRow();
                    // 알아낸 index로 OrderVO에 접근
                    order_VO vo = MyDialog.this.list.get(i);
                    new StatusChange(MyDialog.this, true, vo);

                }
            }
        });

        cnt = true;
        autoData thread = new autoData();
        thread.start();

        this.setVisible(true);

    }

    public void updateData(order_VO vo){
        System.out.println("updateData");
        SqlSession ss = f.factory.openSession();
        int cnt = ss.update("orders.update", vo);
        if(cnt > 0){
            ss.commit();
            table.setValueAt(vo.getO_status(),i,2); // 테이블 컬럼의 2번째 자리
            list.set(i, vo); // list의 vo에 넣음
        }else
            ss.rollback();
        ss.close();
    }

    String[] o_name = {"주문번호", "결제금액", "주문상태", "고객ID"};
    String[][] data;
    public List<order_VO> getData(){
        SqlSession ss = f.factory.openSession();
        List<order_VO> list = ss.selectList("orders.status");
        data = new String[list.size()][o_name.length];
        int i = 0;
        for (order_VO vo : list) {
            data[i][0] = vo.getO_idx();            // 주문번호
            data[i][1] = vo.getO_total_amount();   // 결제금액
            data[i][2] = vo.getO_status();         // 주문상태
            data[i][3] = vo.getUser_id();          // 고객ID
            i++;
        }
        ss.close();
        return list;
    }

    boolean cnt;
    public class autoData extends Thread {
        @Override
        public void run() {
            while (cnt) {
                try {
                    List<order_VO> newList = getData();
                    DefaultTableModel model = new DefaultTableModel(data, o_name) {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };

                    SwingUtilities.invokeLater(() -> {
                        table.setModel(model);
                        list = newList;
                    });
                    System.out.println("table update");
                    Thread.sleep(1000); // 1초마다 갱신
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
