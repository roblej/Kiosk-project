package vo;

public class CouponVO {
    private String u_id;
    private String c_code;
    private String c_start;
    private String c_end;
    private String c_discount_rate; // 할인율
    private String is_coupon_used;     // 0: 미사용, 1: 사용

// --- Getter and Setter ---


    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getC_code() {
        return c_code;
    }

    public void setC_code(String c_code) {
        this.c_code = c_code;
    }

    public String getC_start() {
        return c_start;
    }

    public void setC_start(String c_start) {
        this.c_start = c_start;
    }

    public String getC_end() {
        return c_end;
    }

    public void setC_end(String c_end) {
        this.c_end = c_end;
    }

    public String getC_discount_rate() {
        return c_discount_rate;
    }

    public void setC_discount_rate(String c_discount_rate) {
        this.c_discount_rate = c_discount_rate;
    }

    public String getIs_coupon_used() {
        return is_coupon_used;
    }

    public void setIs_coupon_used(String is_coupon_used) {
        this.is_coupon_used = is_coupon_used;
    }
}