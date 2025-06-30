package client.Login;

import client.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {

    JPanel southPanel,northPanel;
    public JButton inBtn,outBtn,adminBtn;
    MainFrame f;
    Image backgroundImage;
    public LoginPanel(MainFrame f) {
        this.f = f; // MainFrame 인스턴스를 저장

        backgroundImage = new ImageIcon("src/images/test.png").getImage();
        setLayout(new BorderLayout()); // 전체 패널의 레이아웃은 BorderLayout으로 유지

        // --- 북쪽 패널 설정 ---
        northPanel = new JPanel();
        northPanel.setLayout(new FlowLayout(FlowLayout.LEFT)); // 관리자 버튼은 왼쪽에 정렬
//        northPanel.setOpaque(false);

        adminBtn = new JButton("관리자");
        adminBtn.setBackground( Color.WHITE);
        adminBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // JOptionPane은 null을 반환할 수 있으므로, 먼저 null 체크를 해야 합니다.
                String str = JOptionPane.showInputDialog(LoginPanel.this,"관리자 인증 번호를 입력하세요","관리자 로그인", javax.swing.JOptionPane.QUESTION_MESSAGE);
                if(str != null && !str.trim().isEmpty()) { // null 체크 및 빈 문자열 체크
                    // 관리자 인증 로직 추가
                    if("1234".equals(str)) { // 문자열 비교는 .equals() 사용을 권장
                        JOptionPane.showMessageDialog(LoginPanel.this, "관리자 인증 성공", "인증 성공", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        f.cardLayout.show(f.cardPanel, "AdminCard"); // 관리자 페이지로 이동
                    }
                    else
                        JOptionPane.showMessageDialog(LoginPanel.this, "관리자 인증 실패", "인증 실패", JOptionPane.ERROR_MESSAGE);
                } else if (str != null) { // 사용자가 확인은 눌렀지만 입력값이 없는 경우
                    JOptionPane.showMessageDialog(LoginPanel.this, "인증 번호를 입력하세요", "오류", JOptionPane.ERROR_MESSAGE);
                }
                // 사용자가 취소(Cancel)를 누른 경우(str == null)는 아무 동작도 하지 않음
            }
        });
        northPanel.add(adminBtn);
        add(northPanel, BorderLayout.NORTH);

//        // --- 중앙 컨텐츠 라벨 (예시) ---
//        JLabel centerLabel = new JLabel("KIOSK", SwingConstants.CENTER);
//        centerLabel.setFont(new Font("Serif", Font.BOLD, 80)); // 텍스트를 크게 하여 중앙 영역 채우기
//        add(centerLabel, BorderLayout.CENTER);


        // --- 남쪽 패널 설정 (GridBagLayout으로 변경) ---
        southPanel = new JPanel();
        // [수정 1] 레이아웃 매니저를 GridBagLayout으로 변경합니다.
        // GridBagLayout은 복잡하지만 컴포넌트를 원하는 위치에 정확히 배치하는 데 가장 강력합니다.
        southPanel.setLayout(new GridBagLayout());
//        southPanel.setOpaque(false);
        // [수정 2] GridBagConstraints 객체를 사용하여 컴포넌트의 배치 속성을 설정합니다.
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 15, 20, 15); // 컴포넌트 외부 여백 설정 (상, 좌, 하, 우)

        inBtn = new JButton("매장");
        inBtn.setBackground( Color.WHITE);
        outBtn = new JButton("포장");
        outBtn.setBackground( Color.WHITE);

        // 버튼들의 선호 크기를 지정하여 크기를 유지하도록 합니다.
        Dimension buttonSize = new Dimension(150, 60);
        inBtn.setPreferredSize(buttonSize);
        outBtn.setPreferredSize(buttonSize);

        // [수정 3] '매장' 버튼을 (0,0) 위치에 추가
        gbc.gridx = 0;
        gbc.gridy = 0;
        southPanel.add(inBtn, gbc);

        // [수정 4] '포장' 버튼을 (1,0) 위치에 추가
        gbc.gridx = 1;
        gbc.gridy = 0;
        southPanel.add(outBtn, gbc);
        add(southPanel,"South"); // 남쪽 패널을 전체 패널의 남쪽에 추가
        // 전체 패널 배경색 설정
        setBackground(Color.LIGHT_GRAY);

        inBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //매장식사
                //LoginDialog 호출
                MainFrame.orderType = 1;
                LoginDialog loginDialog = null;
                loginDialog = new LoginDialog(f);
                loginDialog.setVisible(true); // 대화상자 표시
            }
        });

        outBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //포장식사
                MainFrame.orderType = 0; // 포장
                LoginDialog loginDialog = null;
                loginDialog = new LoginDialog(f);
                loginDialog.setVisible(true); // 대화상자 표시
            }
        });
    }
    /**
     * [배경 이미지] 배경 이미지를 그리기 위해 paintComponent 메소드를 오버라이드합니다.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // 패널의 전체 크기에 맞춰 이미지를 그립니다.
            g.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}
