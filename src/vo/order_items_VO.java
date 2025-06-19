package vo;

import java.util.List;

public class order_items_VO {
    private String oi_idx, oi_id, product_code, oi_quantity, oi_price, oi_size, options;
    private List<order_VO> list;

    public List<order_VO> getList() {
        return list;
    }

    public void setList(List<order_VO> list) {
        this.list = list;
    }

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
}
