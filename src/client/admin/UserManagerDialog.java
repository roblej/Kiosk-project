package client.admin;

import client.MainFrame;
import org.apache.ibatis.session.SqlSession;
import vo.UserVO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserManagerDialog extends JDialog {
    MainFrame f;
    UserVO vo;
    JPanel mainPanel,idPanel,genderPanel,phonePanel,birthPanel,buttonPanel;
    JLabel idLabel,genderLabel,phoneLabel,birthLabel;
    JTextField idField, genderField, phoneField, birthField;
    JButton updateButton,cancelButton;
    List<UserVO> userList;
    UserManagerPanel p;
    int i;
    public UserManagerDialog(MainFrame f, UserManagerPanel p, boolean modal, UserVO vo, List<UserVO> userList, int i){
        super(f,modal);
        this.f = f;
        this.vo = vo;
        this.userList = userList;
        this.i = i;
        this.p = p;
        setTitle("Update User");
        setSize(400, 300);
        setLocationRelativeTo(f); // 부모 프레임 중앙에 위치

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5,1));

        idPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        idLabel = new JLabel("ID: ");
        idField = new JTextField(vo.getU_id(), 20);
        idPanel.add(idLabel);
        idPanel.add(idField);
        idPanel.add(new JLabel("      ")); // 여백 추가
        mainPanel.add(idPanel);

        genderPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        genderLabel = new JLabel("Gender: ");
        genderField = new JTextField(vo.getU_gender(), 20);
        genderPanel.add(genderLabel);
        genderPanel.add(genderField);
        genderPanel.add(new JLabel("      ")); // 여백 추가
        mainPanel.add(genderPanel);

        phonePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        phoneLabel = new JLabel("Phone: ");
        phoneField = new JTextField(vo.getU_phone(), 20);
        phonePanel.add(phoneLabel);
        phonePanel.add(phoneField);
        phonePanel.add(new JLabel("      ")); // 여백 추가
        mainPanel.add(phonePanel);

        birthPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        birthLabel = new JLabel("Birth: ");
        birthField = new JTextField(vo.getU_birth(), 20);
        birthPanel.add(birthLabel);
        birthPanel.add(birthField);
        birthPanel.add(new JLabel("      ")); // 여백 추가
        mainPanel.add(birthPanel);

        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        updateButton = new JButton("저장");
        cancelButton = new JButton("취소");
        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel);

        this.add(mainPanel);

        // 이벤트 리스너 설정
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 입력된 값을 가져와서 UserVO 객체에 저장 후 쿼리
                UserVO vo = new UserVO();
                vo.setU_id(idField.getText());
                vo.setU_gender(genderField.getText());
                vo.setU_phone(phoneField.getText());
                vo.setU_birth(birthField.getText());

                // SQL 세션을 통해 업데이트 쿼리 실행
                SqlSession ss = f.factory.openSession();
                try {
                    int cnt = ss.update("user.updateUserInfo", vo);
                    ss.commit(); // 변경사항 커밋
                    JOptionPane.showMessageDialog(UserManagerDialog.this, "회원 정보가 업데이트되었습니다.");
                    p.userList.set(i,vo);
                    p.table.setValueAt(vo.getU_id(), i, 0);
                    p.table.setValueAt(vo.getU_birth(), i, 1);
                    p.table.setValueAt(vo.getU_gender(), i, 2);
                    p.table.setValueAt(vo.getU_phone(), i, 3);

                } catch (Exception ex) {
                    ss.rollback(); // 오류 발생 시 롤백
                    JOptionPane.showMessageDialog(UserManagerDialog.this, "업데이트 실패: " + ex.getMessage());
                } finally {
                    ss.close(); // 세션 닫기
                    dispose();

                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 대화상자 닫기
            }
        });
        setVisible(true);
    }
}
