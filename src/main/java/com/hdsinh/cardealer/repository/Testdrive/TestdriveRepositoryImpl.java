package com.hdsinh.cardealer.repository.Testdrive;

import com.hdsinh.cardealer.entities.Testdrive;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.util.StringUtils;

import java.util.List;

public class TestdriveRepositoryImpl implements TestdriveRepositoryCustom{
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Testdrive> loadAll(String search, Integer start, Integer total) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("    td.ID id, ");
        sql.append("    td.full_name name, ");
        sql.append("    td.CODE code, ");
        sql.append("    td.product_id productId, ");
        sql.append("    p.NAME productName, ");
        sql.append("    td.address address, ");
        sql.append("    td.expected_date expectedDate, ");
        sql.append("    td.phone phone, ");
        sql.append("    td.driving_license drivingLicense ");
        sql.append("FROM test_drive td ");
        sql.append("JOIN products p ON td.PRODUCT_ID = p.ID ");
        sql.append("WHERE 1=1 ");

        if (search != null && !search.isEmpty()) {
            sql.append(" and td.NAME like '%").append(search.trim()).append("%'");
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
                .addScalar("code", StandardBasicTypes.STRING)
                .addScalar("phone", StandardBasicTypes.INTEGER)
                .addScalar("productId", StandardBasicTypes.LONG)
                .addScalar("productName", StandardBasicTypes.STRING)
                .addScalar("address", StandardBasicTypes.STRING)
                .addScalar("expectedDate", StandardBasicTypes.DATE)
                .addScalar("drivingLicense", StandardBasicTypes.STRING)
                .setResultTransformer(Transformers.aliasToBean(Testdrive.class));

        return query.getResultList();
    }

    @Override
    public Long countAll(String search) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM test_drive td WHERE 1=1");

        if (!StringUtils.isEmpty(search)) {
            sql.append(" AND td.NAME LIKE :search");
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
