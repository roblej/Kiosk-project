package client.admin;

import client.MainFrame;
import vo.ProductsVO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StockDialog extends JDialog {


    private  JPanel code_panel;
    private  JPanel name_panel;
    private  JPanel price_panel;
    private  JPanel stock_panel;
    private  JPanel select_panel;

    private  JTextField code_tf;
    private  JTextField name_tf;
    private  JTextField price_tf;
    private  JTextField stock_tf;

    private JLabel codelb;
    private JLabel namelb;
    private JLabel pricelb;
    private JLabel stocklb;

    private JButton addBtn;
    private JButton delBtn;
    private JButton okBtn;
    private JButton ccBtn;

    private StockCard callingStockCard;

    public StockDialog(MainFrame f, boolean modal, ProductsVO pvo, StockCard callingStock){

        super(f, modal);
        this.callingStockCard = callingStock;

        setTitle("재고수정");
        setSize(400, 300);
        setLocationRelativeTo(null);
        initComponents();//화면구성
        code_tf.setText(pvo.getP_code());
        name_tf.setText(pvo.getP_name());
        price_tf.setText(pvo.getP_price());
        stock_tf.setText(pvo.getP_stock());

        //이벤트 감지자 등록
        addBtn.addActionListener(new ActionListener() {//추가버튼 클릭시 수행
            @Override
            public void actionPerformed(ActionEvent e) {
                String code = code_tf.getText().trim();
                String name = name_tf.getText().trim();
                String price = price_tf.getText().trim();
                String stock = stock_tf.getText().trim();

                ProductsVO pvo = new ProductsVO();//저장할 객체 생성
                pvo.setP_code(code);
                pvo.setP_name(name);
                pvo.setP_price(price);
                pvo.setP_stock(stock);

                callingStockCard.addData(pvo);
                dispose();
            }
        });

        delBtn.addActionListener(new ActionListener() {//삭제버튼 클릭시 수행
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        ccBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();//현재창 객체를 메모리상에서 삭제한다.
            }
        });

        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //저장버튼을 클릭할 때마다 수행함.
                String code = code_tf.getText().trim();
                String name = name_tf.getText().trim();
                String price = price_tf.getText().trim();
                String stock = stock_tf.getText().trim();

                ProductsVO pvo = new ProductsVO();//저장할 객체 생성
                pvo.setP_code(code);
                pvo.setP_name(name);
                pvo.setP_price(price);
                pvo.setP_stock(stock);

                callingStockCard.updateData(pvo);
                dispose();
            }
        });
        setVisible(true);
    }

    private void initComponents(){
        code_panel = new JPanel();
        name_panel = new JPanel();
        price_panel = new JPanel();
        stock_panel = new JPanel();
        select_panel = new JPanel();

        code_tf = new JTextField();
        name_tf = new JTextField();
        price_tf = new JTextField();
        stock_tf = new JTextField();

        codelb = new JLabel();
        namelb = new JLabel();
        pricelb = new JLabel();
        stocklb = new JLabel();
        addBtn = new JButton();
        delBtn = new JButton();
        okBtn = new JButton();
        ccBtn = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(5,1));

        code_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        codelb.setText("상품코드:");
        code_panel.add(codelb);
        code_tf.setEditable(true);
        code_tf.setColumns(10);
        code_panel.add(code_tf);
        getContentPane().add(code_panel);

        name_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        namelb.setText("상품명:");
        name_panel.add(namelb);
        name_tf.setEditable(true);
        name_tf.setColumns(10);
        name_panel.add(name_tf);
        getContentPane().add(name_panel);

        price_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pricelb.setText("가격:");
        price_panel.add(pricelb);
        price_tf.setEditable(true);
        price_tf.setColumns(10);
        price_panel.add(price_tf);
        getContentPane().add(price_panel);

        stock_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        stocklb.setText("재고:");
        stock_panel.add(stocklb);
        stock_tf.setEditable(true);
        stock_tf.setColumns(10);
        stock_panel.add(stock_tf);
        getContentPane().add(stock_panel);

        addBtn.setText("추가");
        select_panel.add(addBtn);

        delBtn.setText("삭제");
        select_panel.add(delBtn);

        okBtn.setText("저장");
        select_panel.add(okBtn);

        ccBtn.setText("취소");
        select_panel.add(ccBtn);

        getContentPane().add(select_panel);


    }
}
