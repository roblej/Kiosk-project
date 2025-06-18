package Ex;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {

    private JPanel cardPanel; // CardLayout이 적용될 패널
    private CardLayout cardLayout; // CardLayout 매니저

    public MainFrame() {
        setTitle("CardLayout 팀 프로젝트 예시");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null); // 화면 중앙에 배치

        // CardLayout을 적용할 패널 생성
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // 각 카드 패널 인스턴스 생성 및 CardLayout에 추가
        // 각 카드는 별도의 .java 파일(클래스)로 구현됩니다.
        CardPanel1 panel1 = new CardPanel1();
        CardPanel2 panel2 = new CardPanel2();

        cardPanel.add(panel1, "Panel1"); // "Panel1"이라는 이름으로 추가
        cardPanel.add(panel2, "Panel2"); // "Panel2"이라는 이름으로 추가

        // 컨트롤 버튼 생성 (패널 전환용)
        JPanel controlPanel = new JPanel();
        JButton btn1 = new JButton("Panel 1로 이동");
        JButton btn2 = new JButton("Panel 2로 이동");

        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Panel1"); // "Panel1" 이름의 카드 보이기
            }
        });

        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Panel2"); // "Panel2" 이름의 카드 보이기
            }
        });

        controlPanel.add(btn1);
        controlPanel.add(btn2);

        // 프레임에 패널들 추가
        add(cardPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
}