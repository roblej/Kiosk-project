package client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LoginDialog extends JDialog {
    MainFrame f;
    String username,password;

    public LoginDialog(MainFrame f) {
        super(f, "로그인", true); // 모달 대화상자 생성
        setSize(300, 200); // 대화상자 크기 설정
        setLocationRelativeTo(f); // 부모 프레임 중앙에 위치

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,1)); // 수직 박스 레이아웃

        JLabel userLabel = new JLabel("사용자 이름:");
        JTextField userField = new JTextField(15);

        JLabel passLabel = new JLabel("비밀번호:");
        JPasswordField passField = new JPasswordField(15);

        JPanel btn_p = new JPanel();
        JPanel user_p = new JPanel();
        JPanel pass_p = new JPanel();
        JButton btn_login = new JButton("로그인");
        btn_login.addActionListener(e -> {
                    // 로그인 처리 로직 추가
                    username = userField.getText();
                    password = new String(passField.getPassword());
                    if (username.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "사용자 이름과 비밀번호를 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // 로그인 성공 처리
                        JOptionPane.showMessageDialog(this, "로그인 성공!", "성공", JOptionPane.INFORMATION_MESSAGE);
                        dispose(); // 대화상자 닫기
                        //로그인 후 , 주문페이지로 이동
                        login(username, password);
                        f.cardLayout.show(f.cardPanel, "Panel1");
                    }
                });

        JButton btn_register = new JButton("회원가입");
        btn_register.addActionListener(e -> {
                    // 회원가입 처리 로직 추가
                    // 새 JDilog를 열어 회원가입 폼 표시
            RegisterDialog registerDialog = null;
            try {
                registerDialog = new RegisterDialog(this);
                registerDialog.setVisible(true); // 회원가입 대화상자 표시
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
                });
        JButton btn_anonymity = new JButton("비회원주문");
        btn_anonymity.addActionListener(e -> {
                    // 비회원 주문 처리 로직 추가
            //db에 비회원용 아이디 하드코딩
                username = "";
                password = "";
                    dispose(); // 대화상자 닫기
                    f.cardLayout.show(f.cardPanel, "Panel1"); // 비회원 주문 후 Panel1로 이동
        });
        user_p.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pass_p.setLayout(new FlowLayout(FlowLayout.RIGHT));

        btn_p.add(btn_login);
        btn_p.add(btn_register);
        btn_p.add(btn_anonymity);

        user_p.add(userLabel);
        user_p.add(userField);
        user_p.add(new JLabel("   "));

        pass_p.add(passLabel);
        pass_p.add(passField);
        pass_p.add(new JLabel("   "));

        panel.add(user_p);
        panel.add(pass_p);
        panel.add(btn_p);

        add(panel);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // 닫기 버튼 클릭 시 대화상자 닫기
    }
    public void login(String username, String password) {
        // 로그인 로직을 여기에 구현할 수 있습니다.
        // 예: 사용자 이름과 비밀번호를 확인하고, 성공 시 대화상자를 닫고 주문 페이지로 이동합니다.
        // 이 메서드는 버튼 클릭 이벤트에서 호출될 수 있습니다.
    }
}
