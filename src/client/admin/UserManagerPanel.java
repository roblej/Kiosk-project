package client.admin;

import client.MainFrame;
import org.apache.ibatis.session.SqlSession;
import vo.UserVO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class UserManagerPanel extends JPanel {

    JPanel northPanel, southPanel;
    JLabel searchLabel;
    JTextField inputTextField;
    JButton searchBtn,backBtn;

    JTable table;
    String[] c_name = {"id", "생일", "성별","휴대폰번호"};
    String[][] data;

    MainFrame f; // MainFrame 인스턴스 참조
    List<UserVO> userList;

    public UserManagerPanel(MainFrame f) {
        this.f = f;
        this.setLayout(new BorderLayout());
        northPanel = new JPanel();
        northPanel.add(searchLabel = new JLabel("전화번호(뒷자리) : "));
        northPanel.add(inputTextField = new JTextField(12));
        northPanel.add(searchBtn = new JButton("검색"));
        this.add(northPanel, BorderLayout.NORTH);

        southPanel = new JPanel();
        southPanel.add(backBtn = new JButton("뒤로가기"));
        this.add(southPanel, BorderLayout.SOUTH);

        this.add(new JScrollPane(table = new JTable()));
        table.setModel(new DefaultTableModel(data, c_name));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int cnt = e.getClickCount();
                if (cnt == 2) { // 더블 클릭 시
                    System.out.println("test");
                    int i = table.getSelectedRow(); // 선택된 행의 인덱스
                    UserVO vo = userList.get(i);
                    UserManagerDialog dialog = new UserManagerDialog(f, UserManagerPanel.this,true, vo, userList,i);
                }
            }
        });

        inputTextField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = inputTextField.getText();
                if(str.length()>0){
                    search(str);
                }else {
                    JOptionPane.showMessageDialog(UserManagerPanel.this,"전화번호를 입력하세요");
                }
            }
        });
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = inputTextField.getText();
                if(str.length()>0){
                    search(str);
                }else {
                    JOptionPane.showMessageDialog(UserManagerPanel.this,"전화번호를 입력하세요");
                }
            }
        });
        backBtn.addActionListener(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                inputTextField.setText("");
                f.cardLayout.show(f.cardPanel, "AdminCard");
            }
        });
        viewAll(); // 초기 로드 시 전체 회원 조회
    }

    public void search(String phoneNumber){
        //휴대폰번호로 검색기능 구현
        SqlSession ss = f.factory.openSession();
        userList = ss.selectList("user.getUserInfo", phoneNumber);
        viewTable(userList);
    }
    public void viewAll(){
        //전체 회원 조회 기능 구현
        SqlSession ss = f.factory.openSession();
        userList = ss.selectList("user.getAllUserInfo");
        viewTable(userList);
    }
    public void viewTable(List<UserVO> list){
        String[][] data = new String[list.size()][c_name.length];
        for(int i=0; i<list.size(); i++){
            UserVO vo = list.get(i);
            data[i][0] = vo.getU_id();
            data[i][1] = vo.getU_birth();
            data[i][2] = vo.getU_gender();
            data[i][3] = vo.getU_phone();
        }
        table.setModel(new DefaultTableModel(data, c_name){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 셀 편집 불가능
            }
        });
    }
}
