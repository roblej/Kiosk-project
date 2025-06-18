package client;
import javax.swing.*;
import java.awt.*;

public class CardPanel1 extends JPanel {

    public CardPanel1() {
        setBackground(Color.LIGHT_GRAY); // 배경색 설정
        setLayout(new BorderLayout()); // 레이아웃 매니저 설정

        JLabel label = new JLabel("이곳은 첫 번째 카드 패널입니다.", SwingConstants.CENTER);
        label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        add(label, BorderLayout.CENTER);

        // 추가적인 컴포넌트나 로직을 여기에 구현할 수 있습니다.
        // 예를 들어, JButton, JTextField, JTable 등
        JPanel subPanel = new JPanel();
        subPanel.add(new JTextField(10));
        subPanel.add(new JButton("데이터 저장"));
        add(subPanel, BorderLayout.SOUTH);

        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // 여백 추가
    }
}