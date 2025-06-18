package Ex;
import javax.swing.*;
import java.awt.*;

public class CardPanel2 extends JPanel {

    public CardPanel2() {
        setBackground(Color.CYAN); // 배경색 설정
        setLayout(new FlowLayout()); // 레이아웃 매니저 설정

        JLabel label = new JLabel("<html>이곳은 두 번째 카드 패널입니다.<br>다른 내용과 기능을 가질 수 있습니다.</html>", SwingConstants.CENTER);
        label.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        add(label);

        // 여기에 CardPanel2에 특화된 컴포넌트나 기능을 추가합니다.
        JCheckBox checkBox1 = new JCheckBox("옵션 1");
        JCheckBox checkBox2 = new JCheckBox("옵션 2");
        add(checkBox1);
        add(checkBox2);

        setBorder(BorderFactory.createLineBorder(Color.BLUE, 5)); // 테두리 추가
    }
}