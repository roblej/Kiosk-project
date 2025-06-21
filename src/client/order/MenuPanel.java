package client.order; // 패키지 변경

import client.MainFrame;
import org.apache.ibatis.session.SqlSession;
import org.w3c.dom.ls.LSOutput;
import vo.OrderVO;
import vo.ProductsVO;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

/*
이곳은 메뉴판 항목들이 표시되는 부분이다. ProductsVO에서 품목들의 이름, 가격, 사진을 가져와 이곳에 출력하자
 */

public class MenuPanel extends JPanel {

    private OrderPanel orderPanel; // MainFrame 대신 OrderPanel을 참조

    List<ProductsVO> productsList;
    ProductsVO product;
    MainFrame f;

    // 생성자에서 MainFrame 대신 OrderPanel을 받도록 수정
    public MenuPanel(OrderPanel orderPanel, MainFrame f) {
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
        List<ProductsVO> list = ss.selectList("products.getname"); // DB의 products 를 받아오고
        for (ProductsVO vo : list) {
            add(new JButton(vo.getP_name()));
        }
        ss.close();
    }
}