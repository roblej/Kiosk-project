package client.order; // 패키지 변경

import client.MainFrame;
import org.apache.ibatis.session.SqlSession;
import vo.ProductsVO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/*
이곳은 메뉴판 항목들이 표시되는 부분이다. ProductsVO에서 품목들의 이름, 가격, 사진을 가져와 이곳에 출력하자
 */

public class MenuPanel extends JPanel {

    OrderPanel orderPanel; // MainFrame 대신 OrderPanel을 참조

    List<ProductsVO> productsList;
    ProductsVO product;
    MainFrame f;

    // 생성자에서 MainFrame 대신 OrderPanel을 받도록 수정
    public MenuPanel(OrderPanel orderPanel, MainFrame f, ProductsVO p) {
        this.orderPanel = orderPanel;
        this.f = f;
        
        setBackground(Color.WHITE); // 배경 흰색

        // 한 줄에 3개씩 표시되도록 GridLayout으로 설정
        setLayout(new GridLayout(0, 3, 15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        getData();
    }

    public void getData() {
        SqlSession ss = f.factory.openSession();
        List<ProductsVO> list = ss.selectList("products.getname");
        ss.close();

        for (ProductsVO vo : list) {
            JButton btn = new JButton(vo.getP_name());



            // ✅ 버튼에 클릭 리스너 추가
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new OptionDialog(orderPanel, f, vo);
                }
            });
            add(btn); // 메뉴 패널에 버튼 추가
        }

        revalidate(); // 레이아웃 새로고침
        repaint();
    }

}