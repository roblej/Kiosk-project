package vo;

public class order_VO {

    private String o_idx, o_number, o_total_amount, o_status, o_is_takeout, user_id;
    private order_items_VO oiv;

    public order_items_VO getOiv() {
        return oiv;
    }

    public void setOiv(order_items_VO oiv) {
        this.oiv = oiv;
    }

    public String getO_idx() {
        return o_idx;
    }

    public void setO_idx(String o_idx) {
        this.o_idx = o_idx;
    }

    public String getO_number() {
        return o_number;
    }

    public void setO_number(String o_number) {
        this.o_number = o_number;
    }

    public String getO_total_amount() {
        return o_total_amount;
    }

    public void setO_total_amount(String o_total_amount) {
        this.o_total_amount = o_total_amount;
    }

    public String getO_status() {
        return o_status;
    }

    public void setO_status(String o_status) {
        this.o_status = o_status;
    }

    public String getO_is_takeout() {
        return o_is_takeout;
    }

    public void setO_is_takeout(String o_is_takeout) {
        this.o_is_takeout = o_is_takeout;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
