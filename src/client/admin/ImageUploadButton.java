package client.admin;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * 클릭 시 파일 탐색기를 열어 이미지를 선택하고, 지정된 폴더에 저장하는 기능을 가진 커스텀 버튼 클래스입니다.
 * JButton을 상속받아 만듭니다.
 */
class ImageUploadButton extends JButton {

    public ImageUploadButton(String buttonText, Component parentComponent) {
        super(buttonText);

        this.addActionListener(e -> {
            // 1. 파일 탐색기(JFileChooser) 생성
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter imageFilter = new FileNameExtensionFilter(
                    "이미지 파일 (jpg, png, gif)", "jpg", "jpeg", "png", "gif");
            fileChooser.setFileFilter(imageFilter);

            // 2. 파일 탐색기 다이얼로그 열기
            int result = fileChooser.showOpenDialog(parentComponent);

            // 3. 사용자가 파일을 선택하고 '열기' 버튼을 눌렀을 경우
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                // 4. 이미지를 저장할 프로젝트 내 폴더 경로 지정
                String uploadDirPath = System.getProperty("user.dir") + "/src/images";
                File uploadDir = new File(uploadDirPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // 5. 고유한 파일 이름 생성
                String originalFileName = selectedFile.getName();
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
                String uniqueFileName = UUID.randomUUID().toString() + extension;
                Path destinationPath = Paths.get(uploadDir.getAbsolutePath(), uniqueFileName);

                try {
                    // 6. 선택한 파일을 지정된 경로로 복사
                    Files.copy(selectedFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);

                    // 7. 성공 시 콜백 함수를 호출하여 저장된 경로 전달
                    System.out.println("이미지 업로드 성공: " + destinationPath.toString());
                    Path projectPath = Paths.get(System.getProperty("user.dir"));
                    Path relativePath = projectPath.relativize(destinationPath);
                    String dbPathRobust = relativePath.toString().replace('\\', '/');

                    System.out.println("방법 2 (권장): " + dbPathRobust);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    // 8. 실패 시 콜백 함수를 호출하여 에러 메시지 전달
                System.out.println("이미지 업로드 실패: " + ex.getMessage());
                }
            } else {
                // 사용자가 '취소'를 누른 경우
                System.out.println("이미지 업로드가 취소되었습니다.");
                }
        });
    }
}
