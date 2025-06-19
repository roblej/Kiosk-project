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
    SqlSessionFactory factory;

    public MyDialog(MainFrame f, boolean modal, String[][] data, String[] o_name, SqlSessionFactory factory, List<order_VO> list) {
        super(f, modal);
        this.factory = factory;
        this.list = list;
        this.f = f;

        // ✅ 테이블 초기화 (빈 데이터라도 헤더 보이게 하기)
        table = new JTable(new DefaultTableModel(data, o_name) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        // ✅ 스크롤 가능하게 설정
        scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);

        // ✅ 창 설정
        this.setSize(600, 400);
        this.setLocationRelativeTo(null); // 화면 가운데

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // 이벤트 감지자 설정
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // 테이블에서 더블클릭 알아내기
                int cnt = e.getClickCount();
                if(cnt == 2){
                    i = table.getSelectedRow();
                    // 알아낸 index로 OrderVO에 접근
                    order_VO vo = MyDialog.this.list.get(i);
                    Status_Change stChange = new Status_Change(MyDialog.this, true, vo);

                }
            }
        });

        this.setVisible(true);
    }

    public void updateData(order_VO vo){
        System.out.println("updateData");
        SqlSession ss = factory.openSession();
        int cnt = ss.update("orders.update", vo);
        if(cnt > 0){
            ss.commit();
            table.setValueAt(vo.getO_status(),i,2);//이름
            list.set(i, vo);
        }else
            ss.rollback();
        ss.close();
    }

}
