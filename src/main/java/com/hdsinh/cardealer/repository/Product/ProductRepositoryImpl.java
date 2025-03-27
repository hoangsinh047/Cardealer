package com.hdsinh.cardealer.repository.Product;

import com.hdsinh.cardealer.entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Product> loadAll(String search, Integer start, Integer total) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("    p.ID id, ");
        sql.append("    p.NAME name, ");
        sql.append("    p.STATUS status, ");
        sql.append("    p.ODO odo, ");
        sql.append("    p.FIRST_REGIS firstRegis, ");
        sql.append("    p.DESCRIPTION description, ");
        sql.append("    p.MANUFACTURER_ID manufacturerId, ");
        sql.append("    m.NAME manufacturerName, ");
        sql.append("    p.GEARBOX gearbox, ");
        sql.append("    p.FUEL fuel, ");
        sql.append("    p.TYPE type, ");
        sql.append("    p.COLOR color, ");
        sql.append("    p.IMAGE_URL imageUrl, ");
        sql.append("    p.PRICE price, ");
        sql.append("    p.QUANTITY quantity ");
        sql.append("FROM PRODUCTS p ");
        sql.append("JOIN MANUFACTURER m ON p.MANUFACTURER_ID = m.ID ");
        sql.append("WHERE 1=1 ");

        if (search != null && !search.isEmpty()) {
            sql.append(" and p.NAME like '%").append(search.trim()).append("%'");
        }

        if (total != null && total > 0) {
            sql.append(" LIMIT ").append(total);
        }

        if (start != null && start >= 0) {
            sql.append(" OFFSET ").append(start);
        }


        // Khai báo và gán giá trị cho res
        Query query = entityManager.unwrap(Session.class).createNativeQuery(sql.toString())
                .addScalar("id", StandardBasicTypes.LONG)
                .addScalar("name", StandardBasicTypes.STRING)
                .addScalar("status", StandardBasicTypes.STRING)
                .addScalar("odo", StandardBasicTypes.STRING)
                .addScalar("firstRegis", StandardBasicTypes.STRING)
                .addScalar("description", StandardBasicTypes.STRING)
                .addScalar("manufacturerId", StandardBasicTypes.LONG)
                .addScalar("manufacturerName", StandardBasicTypes.STRING)
                .addScalar("gearbox", StandardBasicTypes.STRING)
                .addScalar("fuel", StandardBasicTypes.STRING)
                .addScalar("type", StandardBasicTypes.STRING)
                .addScalar("color", StandardBasicTypes.STRING)
                .addScalar("imageUrl", StandardBasicTypes.STRING)
                .addScalar("price", StandardBasicTypes.BIG_DECIMAL)
                .addScalar("quantity", StandardBasicTypes.INTEGER)
                .setResultTransformer(Transformers.aliasToBean(Product.class));

        return query.getResultList();
    }

    @Override
    public Long countAll(String search) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM PRODUCTS p WHERE 1=1");

        if (!StringUtils.isEmpty(search)) {
            sql.append(" AND p.NAME LIKE :search");
        }

        jakarta.persistence.Query query = entityManager.createNativeQuery(sql.toString());

        if (!StringUtils.isEmpty(search)) {
            query.setParameter("search", "%" + search + "%");
        }

        Object result = query.getSingleResult();
        if (result instanceof Number) {
            return ((Number) result).longValue();
        }
        return 0L;
    }
}
