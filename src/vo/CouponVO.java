package vo;

import java.util.Date;

public class CouponVO {
    private int c_idx;
    private String c_code;
    private Date c_start;
    private Date c_end;
    private double c_discount_rate; // 할인율
    private int is_coupon_used;     // 0: 미사용, 1: 사용

    // --- Getter and Setter ---

    public int getC_idx() {
        return c_idx;
    }

    public void setC_idx(int c_idx) {
        this.c_idx = c_idx;
    }

    public String getC_code() {
        return c_code;
    }

    public void setC_code(String c_code) {
        this.c_code = c_code;
    }

    public Date getC_start() {
        return c_start;
    }

    public void setC_start(Date c_start) {
        this.c_start = c_start;
    }

    public Date getC_end() {
        return c_end;
    }

    public void setC_end(Date c_end) {
        this.c_end = c_end;
    }

    public double getC_discount_rate() {
        return c_discount_rate;
    }

    public void setC_discount_rate(double c_discount_rate) {
        this.c_discount_rate = c_discount_rate;
    }

    public int getIs_coupon_used() {
        return is_coupon_used;
    }

    public void setIs_coupon_used(int is_coupon_used) {
        this.is_coupon_used = is_coupon_used;
    }
}