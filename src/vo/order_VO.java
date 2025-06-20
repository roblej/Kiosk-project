package vo;

public class order_VO {

    private String o_idx, o_number, o_total_amount, o_status, o_is_takeout, user_id;
    private String oi_idx, oi_id, product_code, oi_quantity, oi_price, oi_size, options;


    public String getOi_idx() {
        return oi_idx;
    }

    public void setOi_idx(String oi_idx) {
        this.oi_idx = oi_idx;
    }

    public String getOi_id() {
        return oi_id;
    }

    public void setOi_id(String oi_id) {
        this.oi_id = oi_id;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getOi_quantity() {
        return oi_quantity;
    }

    public void setOi_quantity(String oi_quantity) {
        this.oi_quantity = oi_quantity;
    }

    public String getOi_price() {
        return oi_price;
    }

    public void setOi_price(String oi_price) {
        this.oi_price = oi_price;
    }

    public String getOi_size() {
        return oi_size;
    }

    public void setOi_size(String oi_size) {
        this.oi_size = oi_size;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
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
