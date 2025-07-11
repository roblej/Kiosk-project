package client.Login;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {

    // BCryptPasswordEncoder 인스턴스 (한 번만 생성하여 재사용 권장)
    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 평문 비밀번호를 BCrypt 알고리즘으로 해싱합니다.
     *
     * @param rawPassword 해싱할 평문 비밀번호
     * @return 해싱된 비밀번호 문자열
     */
    public static String hashPassword(String rawPassword) {
        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 비워둘 수 없습니다.");
        }
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * 입력된 평문 비밀번호와 저장된 해싱된 비밀번호가 일치하는지 확인합니다.
     *
     * @param rawPassword         사용자가 입력한 평문 비밀번호
     * @param encodedPassword 데이터베이스에 저장된 해싱된 비밀번호
     * @return 비밀번호가 일치하면 true, 그렇지 않으면 false
     */
    public static boolean checkPassword(String rawPassword, String encodedPassword) {
        if (rawPassword == null || rawPassword.isEmpty() || encodedPassword == null || encodedPassword.isEmpty()) {
            return false; // 유효하지 않은 입력은 불일치로 처리
        }
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
