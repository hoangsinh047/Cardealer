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

import java.math.BigDecimal;
import java.util.List;

@Slf4j
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Product> loadAll(String search, String status, Long manufacturerId,
                                 String type, BigDecimal minPrice, BigDecimal maxPrice, Integer minOdo, Integer maxOdo, String fuel,
                                 String gearbox, Integer start, Integer total) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("    p.ID id, ");
        sql.append("    p.CODE code, ");
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
        sql.append("    p.QUANTITY quantity, ");
        sql.append("    p.num_seat numSeat ");
        sql.append("FROM PRODUCTS p ");
        sql.append("JOIN MANUFACTURER m ON p.MANUFACTURER_ID = m.ID ");
        sql.append("WHERE 1=1 ");

        // Use parameterized query for safer SQL
        if (search != null && !search.isEmpty()) {
            sql.append(" AND p.NAME LIKE :search ");
        }

        if (status != null && !status.isEmpty()) {
            sql.append(" AND p.STATUS = :status ");
        }

        if (manufacturerId != null && manufacturerId > 0) {
            sql.append(" AND p.MANUFACTURER_ID = :manufacturerId ");
        }

        if (type != null && !type.isEmpty()) {
            sql.append(" AND p.TYPE = :type ");
        }

        if (minOdo != null) {
            sql.append(" AND p.ODO >= :minOdo ");
        }

        if (maxOdo != null) {
            sql.append(" AND p.ODO <= :maxOdo ");
        }

        if (fuel != null && !fuel.isEmpty()) {
            sql.append(" AND p.fuel = :fuel ");
        }

        if (gearbox != null && !gearbox.isEmpty()) {
            sql.append(" AND p.gearbox = :gearbox ");
        }

        if (minPrice != null) {
            sql.append(" AND p.PRICE >= :minPrice ");
        }

        if (maxPrice != null) {
            sql.append(" AND p.PRICE <= :maxPrice ");
        }

        // Apply LIMIT and OFFSET
        if (total != null && total > 0) {
            sql.append(" LIMIT :total ");
        }

        if (start != null && start >= 0) {
            sql.append(" OFFSET :start ");
        }

        // Create query and set parameters
        Query query = entityManager.unwrap(Session.class).createNativeQuery(sql.toString())
                .addScalar("id", StandardBasicTypes.LONG)
                .addScalar("code", StandardBasicTypes.STRING)
                .addScalar("name", StandardBasicTypes.STRING)
                .addScalar("status", StandardBasicTypes.STRING)
                .addScalar("odo", StandardBasicTypes.STRING)
                .addScalar("firstRegis", StandardBasicTypes.LOCAL_DATE)
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
                .addScalar("numSeat", StandardBasicTypes.INTEGER)
                .setResultTransformer(Transformers.aliasToBean(Product.class));

        // Set query parameters
        if (search != null && !search.isEmpty()) {
            query.setParameter("search", "%" + search.trim() + "%");
        }

        if (status != null && !status.isEmpty()) {
            query.setParameter("status", status);
        }

        if (manufacturerId != null && manufacturerId > 0) {
            query.setParameter("manufacturerId", manufacturerId);
        }

        if (type != null && !type.isEmpty()) {
            query.setParameter("type", type);
        }

        if (minOdo != null) {
            query.setParameter("minOdo", minOdo);
        }

        if (maxOdo != null) {
            query.setParameter("maxOdo", maxOdo);
        }

        if (fuel != null && !fuel.isEmpty()) {
            query.setParameter("fuel", fuel);
        }

        if (gearbox != null && !gearbox.isEmpty()) {
            query.setParameter("gearbox", gearbox);
        }

        if (minPrice != null) {
            query.setParameter("minPrice", minPrice);
        }

        if (maxPrice != null) {
            query.setParameter("maxPrice", maxPrice);
        }

        if (total != null && total > 0) {
            query.setParameter("total", total);
        }

        if (start != null && start >= 0) {
            query.setParameter("start", start);
        }

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

    public List<Product> findOutOfStockProducts() {
        String hql = "FROM Product p WHERE p.quantity = 0";
        Query<Product> query = (Query<Product>) entityManager.createQuery(hql);
        return query.getResultList();
    }

}
