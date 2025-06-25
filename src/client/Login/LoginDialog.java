package client.Login;

import client.MainFrame;
import org.apache.ibatis.session.SqlSession;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LoginDialog extends JDialog {
    MainFrame f;
    String username,password;

    public LoginDialog(MainFrame f)  {
        super(f, "로그인", true); // 모달 대화상자 생성
        setSize(300, 200); // 대화상자 크기 설정
        setLocationRelativeTo(f); // 부모 프레임 중앙에 위치
        this.f = f; // MainFrame 인스턴스 저장

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3,1)); // 수직 박스 레이아웃

        JLabel userLabel = new JLabel("사용자 이름:");
        JTextField userField = new JTextField(15);

        JLabel passLabel = new JLabel("비밀번호:");
        JPasswordField passField = new JPasswordField(15);

        JPanel btnPanel = new JPanel();
        JPanel userPanel = new JPanel();
        JPanel passPanel = new JPanel();
        JButton loginBtn = new JButton("로그인");
        loginBtn.addActionListener(e -> {
                    // 로그인 처리 로직 추가
                    username = userField.getText();
                    password = new String(passField.getPassword());
                    if (username.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(f, "사용자 이름과 비밀번호를 입력하세요.", "오류", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // 로그인 성공 처리
                        dispose(); // 대화상자 닫기
                        //로그인 후 , 주문페이지로 이동
                        login(username, password);
//                        f.cardLayout.show(f.cardPanel, "orderPanel"); // MainFrame의 cardPanel로 전환
                    }
                });
        passField.addActionListener(e -> {
            loginBtn.doClick();
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
        JButton anonBtn = new JButton("비회원주문");
        anonBtn.addActionListener(e -> {
            // 비회원 주문 처리 로직 추가
            //db에 비회원용 아이디 하드코딩
            username = "unknown_user"; // 비회원 ID 설정
            password = "";
            dispose(); // 대화상자 닫기
            MainFrame.userId = username; // 로그인한 사용자 ID 저장
            f.cardLayout.show(f.cardPanel, "orderPanel"); // "orderPanel"로 이동하도록 변경
        });
        userPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        passPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        btnPanel.add(loginBtn);
        btnPanel.add(btn_register);
        btnPanel.add(anonBtn);

        userPanel.add(userLabel);
        userPanel.add(userField);
        userPanel.add(new JLabel("   "));

        passPanel.add(passLabel);
        passPanel.add(passField);
        passPanel.add(new JLabel("   "));

        panel.add(userPanel);
        panel.add(passPanel);
        panel.add(btnPanel);

        add(panel);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // 닫기 버튼 클릭 시 대화상자 닫기
    }
    public void login(String username, String rawPassword) {
        // 로그인 처리 메서드
            // 1. 입력 유효성 검사
            if (username.isEmpty() || rawPassword.isEmpty()) {
                JOptionPane.showMessageDialog(f, "ID와 비밀번호를 모두 입력하세요.", "경고", JOptionPane.WARNING_MESSAGE);
                return;
            }

            SqlSession ss = null;
            try {
                if (f.factory == null) {
                    throw new IllegalStateException("SqlSessionFactory가 초기화되지 않았습니다.");
                }
                ss = f.factory.openSession();

                // 2. 데이터베이스에서 해당 userId의 해시된 비밀번호 조회
                // user.selectHashedPasswordById 쿼리에 사용자 ID를 파라미터로 전달
                String storedHashedPassword = ss.selectOne("user.login", username);
                // 여기서 selectOne이 Map을 반환하는 대신, 직접 String을 반환하도록 XML을 수정했습니다.

                // 3. 조회된 해시된 비밀번호가 null인지 확인 (사용자 ID가 존재하지 않음)
                if (storedHashedPassword == null) {
                    JOptionPane.showMessageDialog(f, "존재하지 않는 사용자 ID입니다.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
                    return; // 함수 종료
                }

                // 4. 사용자가 입력한 평문 비밀번호와 DB에서 가져온 해시된 비밀번호 비교
                // PasswordUtil.checkPassword() 메서드 사용
                if (PasswordUtil.checkPassword(rawPassword, storedHashedPassword)) {
                    // TODO: 로그인 성공 후 메인 화면의 CardLayout으로 전환
                    MainFrame.userId = username; // 로그인한 사용자 ID 저장
                    f.cardLayout.show(f.cardPanel, "orderPanel"); // MainFrame의 cardPanel로 전환
                    // 로그인 다이얼로그는 닫습니다.
                    dispose();
//                    if (f != null && f.cardLayout != null && f.cardPanel != null) {
//
//                    } else {
//                        System.out.println("MainFrame 인스턴스가 없거나 CardLayout 설정이 올바르지 않습니다.");
//                    }
                } else {
                    JOptionPane.showMessageDialog(f, "비밀번호가 일치하지 않습니다.", "로그인 실패", JOptionPane.ERROR_MESSAGE);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(f, "로그인 중 오류가 발생했습니다: " + ex.getMessage(), "오류", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (ss != null) {
                    ss.close();
                }
            }
        }
    }
