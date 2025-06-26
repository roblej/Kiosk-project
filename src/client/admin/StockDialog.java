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
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class StockDialog extends JDialog {


    private  JPanel main_panel;
    private  JPanel info_panel;
    private  JPanel code_panel;
    private  JPanel name_panel;
    private  JPanel price_panel;
    private  JPanel stock_panel;
    private  JPanel select_panel;
    private  JPanel image_panel;
    private  JPanel imgpath_panel;

    private  JTextField code_tf;
    private  JTextField name_tf;
    private  JTextField price_tf;
    private  JTextField stock_tf;
    private  JTextField img_tf;

    private JLabel codelb;
    private JLabel namelb;
    private JLabel pricelb;
    private JLabel stocklb;
    private JLabel imagelb;
    private JLabel imgpathlb;
    private JButton delBtn;
    private JButton okBtn;
    private JButton ccBtn;

    private JButton plusBtn;
    private JButton minusBtn;

    private StockCard callingStockCard;

    private String dbPath;

    private Path destinationPath;

    public StockDialog(MainFrame f, boolean modal, ProductsVO pvo, StockCard callingStock){

        super(f, modal);
        this.callingStockCard = callingStock;

        setTitle("재고수정");
        setSize(400, 300);
        setLocationRelativeTo(null);

        initComponents(pvo);//화면구성
        code_tf.setText(pvo.getP_code());
        name_tf.setText(pvo.getP_name());
        price_tf.setText(pvo.getP_price());
        stock_tf.setText(pvo.getP_stock());
        img_tf.setText(pvo.getP_image_url());

        //이벤트 감지자 등록

        img_tf.addMouseListener(new MouseAdapter() {//이미지 수정
            @Override
            public void mouseClicked(MouseEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
                        "이미지 파일 (jpg, png, gif)", "jpg", "jpeg", "png", "gif");
                fileChooser.setFileFilter(imageFilter);

                int result = fileChooser.showOpenDialog(null);

                if(result == JFileChooser.APPROVE_OPTION){
                    File selectedFile = fileChooser.getSelectedFile();

                    String uploadDirPath = System.getProperty("user.dir") + "/src/images";
                    System.out.println(uploadDirPath);
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
        plusBtn.addActionListener(new ActionListener() {//재고 증가 버튼
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = stock_tf.getText().trim();
                int num = Integer.parseInt(str);
                num++;
                stock_tf.setText(String.valueOf(num));
            }
        });

        minusBtn.addActionListener(new ActionListener() {//재고감소 버튼
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = stock_tf.getText().trim();
                int num = Integer.parseInt(str);
                num--;
                stock_tf.setText(String.valueOf(num));
            }
        });
        delBtn.addActionListener(new ActionListener() {//삭제버튼 클릭시 수행
            @Override
            public void actionPerformed(ActionEvent e) {
               String code = code_tf.getText().trim();
                callingStockCard.deleteData(code);
                dispose();
            }
        });

        ccBtn.addActionListener(new ActionListener() {//취소버튼 클릭시 수행
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();//현재창 객체를 메모리상에서 삭제한다.
            }
        });

        okBtn.addActionListener(new ActionListener() {//확인버튼 클릭시 수행
            @Override
            public void actionPerformed(ActionEvent e) {
                //저장버튼을 클릭할 때마다 수행함.
                String code = code_tf.getText().trim();
                String name = name_tf.getText().trim();
                String price = price_tf.getText().trim();
                String stock = stock_tf.getText().trim();
                String image = img_tf.getText().trim();

                ProductsVO pvo = new ProductsVO();//저장할 객체 생성
                pvo.setP_code(code);
                pvo.setP_name(name);
                pvo.setP_price(price);
                pvo.setP_stock(stock);
                pvo.setP_image_url(image);

                callingStockCard.updateData(pvo);

                dispose();
            }
        });
        setVisible(true);
    }

    private void initComponents(ProductsVO pvo){
        main_panel = new JPanel(new BorderLayout());
        info_panel = new JPanel();
        imgpath_panel = new JPanel();
        code_panel = new JPanel();
        name_panel = new JPanel();
        price_panel = new JPanel();
        stock_panel = new JPanel();
        select_panel = new JPanel();
        image_panel = new JPanel();

        code_tf = new JTextField();
        name_tf = new JTextField();
        price_tf = new JTextField();
        stock_tf = new JTextField();
        img_tf = new JTextField();

        codelb = new JLabel();
        namelb = new JLabel();
        pricelb = new JLabel();
        stocklb = new JLabel();
        imgpathlb = new JLabel();

        delBtn = new JButton();
        okBtn = new JButton();
        ccBtn = new JButton();
        plusBtn = new JButton();
        minusBtn = new JButton();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        getImage(pvo);

        image_panel.add(imagelb);
        getContentPane().add(main_panel);
        main_panel.add(image_panel, BorderLayout.WEST);

        info_panel.setLayout(new GridLayout(6,1));

        imgpath_panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        imgpathlb = new JLabel("이미지:");
        img_tf.setEditable(true);
        imgpath_panel.add(imgpathlb);
        img_tf.setColumns(10);
        imgpath_panel.add(img_tf);
        info_panel.add(imgpath_panel);

        code_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        codelb.setText("상품코드:");
        code_panel.add(codelb);
        code_tf.setEditable(false);
        code_tf.setColumns(10);
        code_panel.add(code_tf);
        info_panel.add(code_panel);

        name_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        namelb.setText("상품명:");
        name_panel.add(namelb);
        name_tf.setEditable(false);
        name_tf.setColumns(10);
        name_panel.add(name_tf);
        info_panel.add(name_panel);

        price_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pricelb.setText("가격:");
        price_panel.add(pricelb);
        price_tf.setEditable(true);
        price_tf.setColumns(10);
        price_panel.add(price_tf);
        info_panel.add(price_panel);

        stock_panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        stocklb.setText("재고:");
        stock_panel.add(stocklb);
        stock_tf.setHorizontalAlignment(JTextField.CENTER);
        stock_tf.setEditable(true);
        stock_tf.setColumns(4);
        plusBtn.setText("+");
        minusBtn.setText("-");
        stock_panel.add(minusBtn);
        stock_panel.add(stock_tf);
        stock_panel.add(plusBtn);
        info_panel.add(stock_panel);

        delBtn.setText("상품삭제");
        select_panel.add(delBtn);

        okBtn.setText("저장");
        select_panel.add(okBtn);

        ccBtn.setText("취소");
        select_panel.add(ccBtn);

        info_panel.add(select_panel);

        main_panel.add(info_panel, BorderLayout.EAST);



    }
    public void getImage(ProductsVO pvo){
            String s_img = pvo.getP_image_url();
            String fullpath = Paths.get(System.getProperty("user.dir"), "src", s_img).toString();
            System.out.println("가져올이미지경로:" + fullpath);

            ImageIcon icon = new ImageIcon(fullpath);
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(150, 250, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImg);

            if (imagelb != null) {
                imagelb.setIcon(scaledIcon); // 기존 라벨 아이콘만 교체
            } else {
                imagelb = new JLabel(scaledIcon);
                image_panel.add(imagelb);
            }

            image_panel.revalidate();
            image_panel.repaint();


    }
}
