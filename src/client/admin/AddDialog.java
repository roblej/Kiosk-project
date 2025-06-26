package client.admin;

import client.MainFrame;
import vo.ProductsVO;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class AddDialog extends JDialog {
    private  JPanel code_panel;
    private  JPanel name_panel;
    private  JPanel price_panel;
    private  JPanel size_panel;
    private  JPanel option_panel;
    private  JPanel cat_panel;
    private  JPanel img_panel;
    private  JPanel stock_panel;
    private  JPanel select_panel;

    private  JTextField code_tf;
    private  JTextField name_tf;
    private  JTextField price_tf;
    private  JTextField size_tf;
    private  JTextField option_tf;
    private  JTextField cat_tf;
    private  JTextField img_tf;
    private  JTextField stock_tf;

    private JLabel codelb;
    private JLabel namelb;
    private JLabel pricelb;
    private JLabel sizelb;
    private JLabel optionlb;
    private JLabel catlb;
    private JLabel imglb;
    private JLabel stocklb;


    private JButton addBtn;
    private JButton ccBtn;

    private Path destinationPath;
    private String dbPath;

    private StockCard callingStockCard;

    public AddDialog(MainFrame f, boolean modal, ProductsVO pvo, StockCard callingStock){

        super(f, modal);
        this.callingStockCard = callingStock;

        setTitle("상품추가");
        setSize(200, 300);
        setLocationRelativeTo(null);
        initComponents();//화면구성
        code_tf.setText(pvo.getP_code());
        name_tf.setText(pvo.getP_name());
        price_tf.setText(pvo.getP_price());
        size_tf.setText(pvo.getP_size());
        option_tf.setText(pvo.getP_options());
        cat_tf.setText(pvo.getP_category());
        img_tf.setText(pvo.getP_image_url());
        stock_tf.setText(pvo.getP_stock());

        //이벤트 감지자 등록
        img_tf.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//이미지 추가
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
                        "이미지 파일 (jpg, png, gif)", "jpg", "jpeg", "png", "gif");
                fileChooser.setFileFilter(imageFilter);

                int result = fileChooser.showOpenDialog(null);

                if(result == JFileChooser.APPROVE_OPTION){
                    File selectedFile = fileChooser.getSelectedFile();

                    String uploadDirPath = System.getProperty("user.dir") + "/src/images";
                    File uploadDir = new File(uploadDirPath);
                    if(!uploadDir.exists()){
                        uploadDir.mkdirs();
                    }

                    String origianlFileName = selectedFile.getName();
                    String extension = origianlFileName.substring(origianlFileName.lastIndexOf("."));
                    String uniqueFileName = UUID.randomUUID().toString() + extension;
                    destinationPath = Paths.get(uploadDir.getAbsolutePath(), uniqueFileName);

                    try{
                        Files.copy(selectedFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

                        System.out.println("이미지 업로드 성공" + destinationPath.toString());
                        Path projectPath = Paths.get(System.getProperty("user.dir"));
                        Path relativePath = projectPath.relativize(destinationPath);
                        String dbPathRobust = relativePath.toString().replace('\\', '/');

                        int srcIndex = dbPathRobust.indexOf("src/");
                        if(srcIndex != -1){
                            dbPath = dbPathRobust.substring(srcIndex +4);


                        }else {
                            dbPath = dbPathRobust;
                        }
                        System.out.println("DB에 저장될 경로:" + dbPath);
                        img_tf.setText(dbPath);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        System.out.println("이미지 업로드 실패:" + ex.getMessage());
                    }
                }else
                    System.out.println("이미지 업로드가 취소되었습니다.");
            }
        });

        ccBtn.addActionListener(new ActionListener() {//취소버튼 클릭시 수행
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();//현재창 객체를 메모리상에서 삭제한다.
            }
        });

        addBtn.addActionListener(new ActionListener() {//추가버튼 클릭시 수행
            @Override
            public void actionPerformed(ActionEvent e) {
                //추가버튼을 클릭할 때마다 수행함.
                String code = code_tf.getText().trim();
                if(code == null || code.trim().isEmpty()){JOptionPane.showMessageDialog(null, "상품코드를 입력하세요");
                return;}
                String name = name_tf.getText().trim();
                if(name == null || name.trim().isEmpty()){JOptionPane.showMessageDialog(null, "상품명을 입력하세요");
                return;}
                String price = price_tf.getText().trim();
                if(price == null || price.trim().isEmpty()){JOptionPane.showMessageDialog(null, "가격을 입력하세요");
                return;}
                String size = size_tf.getText().trim();
                if(size == null) size = "";

                String option = option_tf.getText().trim();
                if(option == null) option = "";

                String category = cat_tf.getText().trim();
                if(category == null || category.trim().isEmpty()){JOptionPane.showMessageDialog(null, "카테고리를 입력하세요");
                return;}
                String img = img_tf.getText().trim();
                if(img == null || img.trim().isEmpty()){JOptionPane.showMessageDialog(null, "이미지를 입력하세요");
                return;}
                String stock = stock_tf.getText().trim();
                if(stock == null || stock.trim().isEmpty()){JOptionPane.showMessageDialog(null, "재고를 입력하세요");
                return;}

                ProductsVO pvo = new ProductsVO();//저장할 객체 생성
                pvo.setP_code(code);
                pvo.setP_name(name);
                pvo.setP_price(price);
                pvo.setP_size(size);
                pvo.setP_options(option);
                pvo.setP_category(category);
                pvo.setP_image_url(img);
                pvo.setP_stock(stock);

                callingStockCard.addData(pvo);
                dispose();
            }
        });
        setVisible(true);
    }

    private void initComponents(){
        code_panel = new JPanel();
        name_panel = new JPanel();
        price_panel = new JPanel();
        size_panel = new JPanel();
        option_panel = new JPanel();
        cat_panel = new JPanel();
        img_panel = new JPanel();
        stock_panel = new JPanel();
        select_panel = new JPanel();

        code_tf = new JTextField();
        name_tf = new JTextField();
        price_tf = new JTextField();
        size_tf = new JTextField();
        option_tf = new JTextField();
        cat_tf = new JTextField();
        img_tf = new JTextField();
        stock_tf = new JTextField();

        codelb = new JLabel();
        namelb = new JLabel();
        pricelb = new JLabel();
        sizelb = new JLabel();
        optionlb = new JLabel();
        catlb = new JLabel();
        imglb = new JLabel();
        stocklb = new JLabel();

        addBtn = new JButton();
        ccBtn = new JButton();


        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(9,1));

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

        size_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        sizelb.setText("사이즈:");
        size_panel.add(sizelb);
        size_tf.setEditable(true);
        size_tf.setColumns(10);
        size_panel.add(size_tf);
        getContentPane().add(size_panel);

        option_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        optionlb.setText("옵션:");
        option_panel.add(optionlb);
        option_tf.setEditable(true);
        option_tf.setColumns(10);
        option_panel.add(option_tf);
        getContentPane().add(option_panel);

        cat_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        catlb.setText("카테고리:");
        cat_panel.add(catlb);
        cat_tf.setEditable(true);
        cat_tf.setColumns(10);
        cat_panel.add(cat_tf);
        getContentPane().add(cat_panel);

        img_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        imglb.setText("이미지:");
        img_panel.add(imglb);
        img_tf.setEditable(true);
        img_tf.setColumns(10);
        img_panel.add(img_tf);
        getContentPane().add(img_panel);

        stock_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        stocklb.setText("재고:");
        stock_panel.add(stocklb);
        stock_tf.setEditable(true);
        stock_tf.setColumns(10);
        stock_panel.add(stock_tf);
        getContentPane().add(stock_panel);

        addBtn.setText("추가");
        select_panel.add(addBtn);
        ccBtn.setText("취소");
        select_panel.add(ccBtn);
        getContentPane().add(select_panel);


    }
}
