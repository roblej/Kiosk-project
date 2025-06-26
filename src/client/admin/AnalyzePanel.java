package client.admin;

import client.MainFrame;
import org.apache.ibatis.session.SqlSession;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class AnalyzePanel extends JPanel {
    JCheckBox[] chk_ar;
    JPanel center_p, north_p;
    String[] option = {"10대", "20대", "30대", "40대", "50대", "남성", "여성"};
    MainFrame f;
    JLabel searchLabel;
    JTextField searchField;
    JButton searchBtn,
            backBtn;;
    String[] c_name = {"상품명","수량"};
//    String[] c_name = {"아이디","상품명","가격","포장여부","수량"};
    String[][] data;
    JTable table;

    public AnalyzePanel(MainFrame f) {
        this.f = f;
        setLayout(new BorderLayout());
        north_p = new JPanel();
        north_p.setLayout(new GridLayout(2, 1, 10, 10));
        JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        chk_ar = new JCheckBox[option.length];
        for (int i = 0; i < option.length; i++) {
            chk_ar[i] = new JCheckBox(option[i]);
            checkBoxPanel.add(chk_ar[i]);
        }

        searchLabel = new JLabel("아이디 : ");
        searchField = new JTextField(10);
        searchBtn = new JButton("검색");
        JPanel searchPanel = new JPanel();
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchBtn);

        north_p.add(checkBoxPanel);
        north_p.add(searchPanel);

        this.add(north_p, BorderLayout.NORTH);

        center_p = new JPanel();
        center_p.add(new JScrollPane(table = new JTable(data, c_name)));
        table.setModel(new DefaultTableModel(data, c_name));

        this.add(center_p, BorderLayout.CENTER);
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 검색 버튼 클릭 시 동작
                search();
            }
        });
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 검색 버튼 클릭 시 동작
                search();
        }
        });
        backBtn = new JButton("뒤로가기");
        JPanel southPanel = new JPanel();
        southPanel.add(backBtn);
        this.add(southPanel, BorderLayout.SOUTH);
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //체크버튼 해제, 검색필드 초기화
                for (JCheckBox chk : chk_ar) {
                    chk.setSelected(false);
                }
                searchField.setText("");
                f.cardLayout.show(f.cardPanel, "AdminCard");
            }
        });

        center_p = new JPanel();

        setVisible(true);
    }
    /**
     * 체크박스와 텍스트 필드의 값을 기반으로 DB에서 판매 데이터를 조회하고,
     * 결과를 JTable에 업데이트하는 메소드입니다.
     */
    public void search() {
        // 1. MyBatis 쿼리에 전달할 파라미터 맵(Map)을 생성합니다.
        Map<String, Object> params = new HashMap<>();

        List<String> selectedAgeRanges = new ArrayList<>();

        // --- [수정] 성별 파라미터 처리 로직 변경 ---
        boolean isMaleSelected = false;
        boolean isFemaleSelected = false;

        for (JCheckBox chk : chk_ar) {
            if (chk.isSelected()) {
                String text = chk.getText();
                if (text.endsWith("대")) {
                    selectedAgeRanges.add(text);
                } else if ("남성".equals(text)) {
                    isMaleSelected = true;
                } else if ("여성".equals(text)) {
                    isFemaleSelected = true;
                }
            }
        }

        // 남성과 여성 중 하나만 선택된 경우에만 gender 파라미터를 추가합니다.
        if (isMaleSelected && !isFemaleSelected) {
            params.put("gender", "M");
        } else if (!isMaleSelected && isFemaleSelected) {
            params.put("gender", "F");
        }

        // [수정] 선택된 나이대 리스트가 비어있지 않다면, params 맵에 추가합니다.
        if (!selectedAgeRanges.isEmpty()) {
            params.put("ageRanges", selectedAgeRanges);
        }

        // 아이디 검색 필드 값을 파라미터에 추가합니다.
        String searchText = searchField.getText().trim();
        if (!searchText.isEmpty()) {
            params.put("userId", searchText);
        }

        try {
            // 2. MyBatis를 실행하여 데이터를 조회합니다.
            // MainFrame에 SqlSession을 가져오는 메소드가 있다고 가정합니다. (예: f.getSqlSession())
            SqlSession ss = f.factory.openSession();
            List<Map<String, Object>> resultList = ss.selectList("analysis.selectSalesAnalysis", params);

            // 3. 조회 결과를 JTable에 표시할 2차원 배열(data)로 변환합니다.
            data = new String[resultList.size()][c_name.length];
            for (int i = 0; i < resultList.size(); i++) {
                Map<String, Object> rowMap = resultList.get(i);
//                data[i][0] = String.valueOf(rowMap.get("u_id"));
                data[i][0] = String.valueOf(rowMap.get("p_name"));
                // 가격 포맷팅 (DB에서 숫자로 반환된 값을 콤마를 포함한 문자열로)
//                data[i][2] = String.format("%,d", ((Number) rowMap.get("p_price")).intValue());
                // 포장여부 변환 ("1" -> "포장", "0" -> "매장")
//                data[i][3] = "1".equals(String.valueOf(rowMap.get("o_is_takeout"))) ? "포장" : "매장";
                data[i][1] = String.valueOf(rowMap.get("total_quantity"));
            }

            // 4. 테이블 모델을 새로운 데이터로 업데이트하고, 테이블을 새로고침합니다.
            table.setModel(new DefaultTableModel(data, c_name){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; // 테이블 셀 편집 불가
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "데이터 조회 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
        }
    }
}
