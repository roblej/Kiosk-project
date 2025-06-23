package client.order;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import java.util.List;

public class ProductsDao {

    private SqlSessionFactory factory;

    public ProductsDao(SqlSessionFactory factory) {
        this.factory = factory;
    }

    public List<String> getCategories() {
        List<String> categories = null;
        try (SqlSession session = factory.openSession()) {
            categories = session.selectList("products.getCategories");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }
}