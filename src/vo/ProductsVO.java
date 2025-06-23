package vo;

public class ProductsVO {
    private String p_code, p_name, p_price, p_size, p_options, p_category, p_image_url, p_stock;

    // 장바구니에 적용하기 위한 멤버변수
    private int price, count;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getP_code() {
        return p_code;
    }

    public void setP_code(String p_code) {
        this.p_code = p_code;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_price() {
        return p_price;
    }

    public void setP_price(String p_price) {
        this.p_price = p_price;
    }

    public String getP_size() {
        return p_size;
    }

    public void setP_size(String p_size) {
        this.p_size = p_size;
    }

    public String getP_options() {
        return p_options;
    }

    public void setP_options(String p_options) {
        this.p_options = p_options;
    }

    public String getP_category() {
        return p_category;
    }

    public void setP_category(String p_category) {
        this.p_category = p_category;
    }

    public String getP_image_url() {
        return p_image_url;
    }

    public void setP_image_url(String p_image_url) {
        this.p_image_url = p_image_url;
    }

    public String getP_stock() {
        return p_stock;
    }

    public void setP_stock(String p_stock) {
        this.p_stock = p_stock;
    }
}
