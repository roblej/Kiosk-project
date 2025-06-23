package client.admin;

import client.MainFrame;
import org.apache.ibatis.session.SqlSession;
import vo.CouponVO;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class CouponManagerDialog extends JDialog {
    MainFrame f;
    CouponVO vo;
    List<CouponVO> couponList;
    CouponManagerPanel p;
    int i;
    JButton updateBtn, deleteBtn;
    JPanel mainPanel,buttonPanel,
            namePanel, ratePanel, usePanel, createDatePanel, expiryDatePanel;
    JTextField nameField, rateField, useField,
            createDateField, expiryDateField;;
    public CouponManagerDialog(MainFrame f, CouponManagerPanel p, boolean modal, CouponVO vo, List<CouponVO> couponList, int i) {
        super(f,modal);
        this.f = f;
        this.vo = vo;
        this.couponList = couponList;
        this.i = i;
        this.p = p;

        setTitle("Update Coupon");
        setSize(400, 300);
        setLocationRelativeTo(f); // 부모 프레임 중앙에 위치


        updateBtn = new JButton("수정");
        deleteBtn = new JButton("삭제");

        buttonPanel = new JPanel();
        buttonPanel.add(updateBtn);
        buttonPanel.add(deleteBtn);
        add(buttonPanel, "South");

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        namePanel = new JPanel();
        namePanel.add(new JLabel("사용자명: "));
        nameField = new JTextField(vo.getU_id(), 20);
        namePanel.add(nameField);
        mainPanel.add(namePanel);

        ratePanel = new JPanel();
        ratePanel.add(new JLabel("할인율: "));
        rateField = new JTextField(String.valueOf(vo.getC_discount_rate()), 20);
        ratePanel.add(rateField);
        mainPanel.add(ratePanel);

        usePanel = new JPanel();
        usePanel.add(new JLabel("사용가능여부: "));
        useField = new JTextField(vo.getIs_coupon_used(), 20);
        usePanel.add(useField);
        mainPanel.add(usePanel);

        createDatePanel = new JPanel();
        createDatePanel.add(new JLabel("발급일: "));
        createDateField = new JTextField(vo.getC_start(), 20);
        createDatePanel.add(createDateField);
        mainPanel.add(createDatePanel);

        expiryDatePanel = new JPanel();
        expiryDatePanel.add(new JLabel("만료일: "));
        expiryDateField = new JTextField(vo.getC_end(), 20);
        expiryDatePanel.add(expiryDateField);
        mainPanel.add(expiryDatePanel);

        this.add(mainPanel);


        updateBtn.addActionListener(e -> {
            // 수정 로직 구현
            // 예: vo 객체의 필드 값을 수정하고 DB에 업데이트
            // 이후 p.couponList를 갱신하고 테이블을 새로 고침
            System.out.println("수정 버튼 클릭됨");
            CouponVO vo1 = new CouponVO();

            vo1.setC_code(vo.getC_code());
            vo1.setU_id(nameField.getText());
            vo1.setC_discount_rate(rateField.getText());
            vo1.setIs_coupon_used(useField.getText());
            vo1.setC_start(createDateField.getText());
            vo1.setC_end(expiryDateField.getText());

            SqlSession ss = f.factory.openSession();
            int cnt = ss.update("coupon.update", vo1);
            if (cnt > 0) {
                ss.commit();
                dispose();
                p.showAll(); // 쿠폰 목록 갱신
            } else {
                ss.rollback();
                JOptionPane.showMessageDialog(this, "쿠폰 정보 수정에 실패했습니다.");
            }

        });
        deleteBtn.addActionListener(e -> {
            // 삭제 로직 구현
            // 예: vo 객체를 couponList에서 제거하고 DB에서 삭제
            // 이후 p.couponList를 갱신하고 테이블을 새로 고침
            System.out.println("삭제 버튼 클릭됨");
            SqlSession ss = f.factory.openSession();
            int cnt = ss.delete("coupon.delete", vo.getC_code());
            if (cnt > 0) {
                ss.commit();
                JOptionPane.showMessageDialog(this, "쿠폰이 삭제되었습니다.");
                dispose();
                p.showAll(); // 쿠폰 목록 갱신
            } else {
                ss.rollback();
                JOptionPane.showMessageDialog(this, "쿠폰 삭제에 실패했습니다.");
            }
        });
        setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });


    }
}
