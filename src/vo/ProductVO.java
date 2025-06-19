package vo;

public class ProductVO {
    private String p_code;
    private String p_name;
    private int p_price;
    private String p_size;
    private String p_options;
    private String p_category;
    private String p_image_url;
    private int p_stock;

    // 모든 필드에 대한 Getters and Setters
    public String getP_code() { return p_code; }
    public void setP_code(String p_code) { this.p_code = p_code; }
    public String getP_name() { return p_name; }
    public void setP_name(String p_name) { this.p_name = p_name; }
    public int getP_price() { return p_price; }
    public void setP_price(int p_price) { this.p_price = p_price; }
    public String getP_size() { return p_size; }
    public void setP_size(String p_size) { this.p_size = p_size; }
    public String getP_options() { return p_options; }
    public void setP_options(String p_options) { this.p_options = p_options; }
    public String getP_category() { return p_category; }
    public void setP_category(String p_category) { this.p_category = p_category; }
    public String getP_image_url() { return p_image_url; }
    public void setP_image_url(String p_image_url) { this.p_image_url = p_image_url; }
    public int getP_stock() { return p_stock; }
    public void setP_stock(int p_stock) { this.p_stock = p_stock; }
}