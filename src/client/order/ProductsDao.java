package client.order;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import vo.ProductsVO; // ProductsVO 임포트
import java.util.List;

public class ProductsDao {

    private SqlSessionFactory factory;

    public ProductsDao(SqlSessionFactory factory) {
        this.factory = factory;
    }

    // 1. 모든 상품 조회하는 메소드 (ID 변경에 맞춰 메소드 이름 변경)
    public List<ProductsVO> all() {
        try (SqlSession session = factory.openSession()) {
            // "products.getname" 대신 "products.all" 호출
            return session.selectList("products.all");
        }
    }

    // 2. 카테고리별 상품 조회 메소드 추가
    public List<ProductsVO> getProductsByCategory(String category) {
        try (SqlSession session = factory.openSession()) {
            return session.selectList("products.getProductsByCategory", category);
        }
    }

    // 3. 카테고리 목록 조회 메소드
    public List<String> getCategories() {
        try (SqlSession session = factory.openSession()) {
            return session.selectList("products.getCategories");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}