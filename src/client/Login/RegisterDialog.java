package client.Login;

import org.apache.ibatis.session.SqlSession;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegisterDialog extends JDialog {
    // 회원가입 대화상자 구현
    // 이 클래스는 회원가입 관련 UI와 기능을 포함합니다.
    String username, password, gender,phone,birth;
    String rawPassword;
    JPanel panel,birthPanel,genderPanel,phonePanel,userPanel,passPanel;
    JLabel userLabel, passLabel, genderLabel, phoneLabel, birthLabel;
    JRadioButton maleRadio, femaleRadio;
    JTextField phoneField, birthField, userField;
    JPasswordField passField;
    JButton registerBtn;
    LoginDialog D;
    public RegisterDialog(LoginDialog D) throws IOException {
        // 초기화 코드 작성
        // 예: UI 컴포넌트 생성 및 레이아웃 설정
        super(D, "회원가입", true); // 모달 대화상자 생성
        setSize(300, 300); // 대화상자 크기 설정
        setLocationRelativeTo(null); // 화면 중앙에 위치
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // 닫기 버튼 클릭 시 대화상자 닫기
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // 수직 박스 레이아웃
        this.D = D; // LoginDialog 인스턴스 저장

        userPanel = new JPanel();
        userLabel = new JLabel("사용자 이름:");
        userField = new JTextField(15);
        userPanel.add(userLabel);
        userPanel.add(userField);
        userPanel.add(new JLabel("   "));
        userPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        passPanel = new JPanel();
        passLabel = new JLabel("비밀번호:");
        passField = new JPasswordField(15);
        passPanel.add(passLabel);
        passPanel.add(passField);
        passPanel.add(new JLabel("   "));
        passPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        genderPanel = new JPanel();
        genderLabel = new JLabel("성별:");
        maleRadio = new JRadioButton("남성");
        femaleRadio = new JRadioButton("여성");
        ButtonGroup group = new ButtonGroup();

        group.add(maleRadio);
        group.add(femaleRadio);

//        genderPanel.setLayout(new BoxLayout(genderPanel, BoxLayout.X_AXIS));
        genderPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        genderPanel.add(genderLabel);
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        genderPanel.add(new JLabel("   "));

        phoneLabel = new JLabel("전화번호:");
        phoneField = new JTextField(15);
        phonePanel = new JPanel();
        phonePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        phonePanel.add(phoneLabel);
        phonePanel.add(phoneField);
        phonePanel.add(new JLabel("   "));

        birthLabel = new JLabel("생년월일:");
        birthField = new JTextField(15);
        birthPanel = new JPanel();
        birthPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        birthPanel.add(birthLabel);
        birthPanel.add(birthField);
        birthPanel.add(new JLabel("   "));

        registerBtn = new JButton("회원가입");

        registerBtn.addActionListener(e -> {
                    // 회원가입 처리 로직 추가
                    username = userField.getText();
                    rawPassword = new String(passField.getPassword());
                    register(username);
                    //회원가입 이후 로그인페이지로
                    dispose();
                });

        panel.add(userPanel);
        panel.add(passPanel);
        panel.add(phonePanel);
        panel.add(birthPanel);
        panel.add(genderPanel);
        panel.add(registerBtn);

        this.add(panel);

        // 한번만 수행
    }

    // 회원가입 버튼 클릭 시 호출되는 메서드
    public void register(String username) {
        // 회원가입 로직
         //비밀번호 유효성 검사 (필수 입력 등)
            if (rawPassword.isEmpty()) {
                JOptionPane.showMessageDialog(RegisterDialog.this, "비밀번호를 입력하세요.", "경고", JOptionPane.WARNING_MESSAGE);
                return; // 비밀번호가 없으면 처리 중단
            }

            // 비밀번호 해싱
//            username = userField.getText(); // 사용자 이름 가져오기
            password = PasswordUtil.hashPassword(rawPassword);// 해싱된 비밀번호를 저장
            gender = (maleRadio.isSelected() ? "M" : (femaleRadio.isSelected() ? "F" : "선택 안됨"));
            phone = phoneField.getText(); // 전화번호 가져오기
            birth = birthField.getText(); // 생년월일 가져오기
            // 회원가입 정보 출력 (디버깅용)

            // TODO: 여기에 실제 DB 저장 로직 (MyBatis Mapper 호출 등) 구현
            SqlSession ss = D.f.factory.openSession();
            Map<String,String> map = new HashMap<>();
            map.put("u_id", username);
            map.put("u_password", password);
            map.put("u_gender", gender);
            map.put("u_phone", phone);
            map.put("u_birth", birth);
            // MyBatis Mapper 호출
            try {
                int result = ss.insert("user.register", map); // UserMapper.xml에 정의된 insertUser 메서드 호출
                ss.commit(); // 트랜잭션 커밋
                if (result > 0) {
                    System.out.println("회원가입 성공");
                    JOptionPane.showMessageDialog(RegisterDialog.this, "회원가입이 완료되었습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    System.out.println("회원가입 실패");
                }
            } catch (Exception e) {
                e.printStackTrace();
                ss.rollback(); // 예외 발생 시 롤백
            } finally {
                ss.close(); // SqlSession 닫기
                dispose();
            }
    }

}
