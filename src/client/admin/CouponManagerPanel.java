package client.admin;

import client.MainFrame;
import org.apache.ibatis.session.SqlSession;
import vo.CouponVO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.security.SecureRandom;

import static java.time.LocalDate.now;

public class CouponManagerPanel extends JPanel {
    //쿠폰관리창
    MainFrame f;
    JLabel couponLabel,nameLabel, rateLabel, useLabel, createDateLabel, expiryDateLabel;
    JTextField searchTextField,nameTextField, rateTextField, useTextField, createDateTextField, expiryDateTextField;
    JTable table;
    JPanel northPanel,southPanel;
    JButton searchBtn,createBtn,backBtn;
    List<CouponVO> couponList;

    String[] t_name = {"사용자명","쿠폰번호", "할인율", "사용가능여부", "발급일", "만료일"};
    String[][] data;
    public  CouponManagerPanel(MainFrame f) {
        this.f = f;
        this.setLayout(new BorderLayout());
        northPanel = new JPanel();
        couponLabel = new JLabel("쿠폰검색(ID) : ");

        northPanel.add(couponLabel);
        northPanel.add(searchTextField = new JTextField(12));
        northPanel.add(searchBtn = new JButton("검색"));
        this.add(northPanel, BorderLayout.NORTH);
        this.add(new JScrollPane(table = new JTable()));
        table.setModel(new DefaultTableModel(data, t_name));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int cnt = e.getClickCount();
                if (cnt == 2) { // 더블 클릭 시
                    int i = table.getSelectedRow(); // 선택된 행의 인덱스
                    CouponVO vo = couponList.get(i);
                   CouponManagerDialog dialog = new CouponManagerDialog(f,CouponManagerPanel.this, true, vo, couponList, i);

                }
            }
        });
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = searchTextField.getText();
                if (id.isEmpty()) {
                    showAll();
                } else {
                    search(id);
                }
            }
        });
        southPanel = new JPanel(new GridLayout(5,1));
//        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
//        southPanel.setBorder((new EmptyBorder(0,50,0,50)));
        JPanel namePanel = new JPanel();
        namePanel.add(nameLabel = new JLabel("아이디 :"));
        namePanel.add(nameTextField = new JTextField(10));
        southPanel.add(namePanel);

        JPanel ratePanel = new JPanel();
        ratePanel.add(rateLabel = new JLabel("할인율 :"));
        ratePanel.add(rateTextField = new JTextField(10));
        southPanel.add(ratePanel);

        JPanel createPanel = new JPanel();
        createPanel.add(createDateLabel = new JLabel("발급일 :"));
        createPanel.add(createDateTextField = new JTextField(10));
        createDateTextField.setText(now().toString());
        southPanel.add(createPanel);

        JPanel expiryPanel = new JPanel();
        expiryPanel.add(expiryDateLabel = new JLabel("만료일 :"));
        expiryPanel.add(expiryDateTextField = new JTextField(10));
        southPanel.add(expiryPanel);

        JPanel btnPanel = new JPanel();
        btnPanel.add(backBtn = new JButton("뒤로가기"));
        btnPanel.add(createBtn= new JButton("쿠폰 발급"));
        southPanel.add(btnPanel);
        this.add(southPanel, BorderLayout.SOUTH);
        showAll();
        createBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createCoupon();
            }
        });

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.cardLayout.show(f.cardPanel, "AdminCard");
            }
        });

        setVisible(true);
    }
    public void search(String id){
        //휴대폰번호로 검색기능 구현
        SqlSession ss = f.factory.openSession();
        couponList = ss.selectList("coupon.search", id);
        viewTable(couponList);
    }
    public void showAll(){
        //휴대폰번호로 검색기능 구현
        SqlSession ss = f.factory.openSession();
        couponList = ss.selectList("coupon.all");
        viewTable(couponList);
    }

    private void viewTable(List<CouponVO> list){
        String[][] data = new String[list.size()][t_name.length];
        for(int i=0; i<list.size(); i++){
            CouponVO vo = list.get(i);
            data[i][0] = vo.getU_id();
            data[i][1] = vo.getC_code();
            data[i][2] = vo.getC_discount_rate();
            if(vo.getIs_coupon_used().equals("0")){
                data[i][3] = "사용안함";
            }else{
                data[i][3] = "사용됨";
            }
            data[i][4] = vo.getC_start();
            data[i][5] = vo.getC_end();
        }
        table.setModel(new DefaultTableModel(data, t_name){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // 셀 편집 불가능
            }
        });
    }

    private void createCoupon() {
        SqlSession ss = f.factory.openSession();
        String newCouponCode;
        CouponVO coupon = new CouponVO();

        if (nameTextField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "아이디를 입력해주세요");
            System.out.println(nameTextField.getWidth());
            return;
        }
        else if (rateTextField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "할인율을 입력해주세요");
            return;
        }
        else if(expiryDateTextField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "만료일을 입력해주세요");
            return;
        }

            // 1. DB와 중복되지 않는 유니크한 쿠폰 코드 생성

            while (true) {
                newCouponCode = generateRandomAlphanumericString(10); // 8자리 난수 생성
                String newUserid = nameTextField.getText().trim();
                // 2. MyBatis를 통해 DB에 코드가 존재하는지 확인
                int count = ss.selectOne("coupon.checkCodeExists", newCouponCode);
                int cnt = ss.selectOne("coupon.checkUserid",newUserid);
                // 3. 코드가 존재하지 않으면(count가 0이면) 루프 탈출
                if (count == 0 && cnt >= 1) {
                 break;
                }else{
                 JOptionPane.showMessageDialog(null,"존재하지 않는 아이디입니다");
                 return;
                }
//                if(cnt == 0) { //사용자 아이디가 존재하는지 확인
//                 return;
//                }
                // 코드가 존재하면 루프를 다시 실행하여 새로운 코드를 생성

            }


            coupon.setC_code(newCouponCode);
            coupon.setU_id(nameTextField.getText());
            coupon.setC_discount_rate(rateTextField.getText());
            coupon.setIs_coupon_used("0");
            coupon.setC_start(String.valueOf(now()));
            coupon.setC_end(expiryDateTextField.getText());
            int result = ss.insert("coupon.create", coupon);
            if (result > 0) {
                ss.commit();
                JOptionPane.showMessageDialog(this, "쿠폰이 성공적으로 발급되었습니다.");
                showAll(); // 쿠폰 목록 갱신
            } else {
                ss.rollback();
                JOptionPane.showMessageDialog(this, "쿠폰 발급에 실패했습니다.");

        }
            nameTextField.setText("");
            rateTextField.setText("");
            expiryDateTextField.setText("");

    }
    private String generateRandomAlphanumericString(int length) {
        // 쿠폰 코드에 사용될 문자들
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }
}
