package com.hdsinh.cardealer.repository.Bill;

import com.hdsinh.cardealer.dto.BillDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.util.StringUtils;

import java.util.List;

public class BillRepositoryImpl implements BillRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<BillDto> loadAll(String search, Integer start, Integer total) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("    s.ID id, ");
        sql.append("    e.CODE code, ");
        sql.append("    e.NAME name, ");
        sql.append("    e.PHONE phone, ");
        sql.append("    e.ADDRESS address, ");
        sql.append("    s.PRODUCT_ID productId, ");
        sql.append("    s.EMPLOYEE_ID employeeId, ");
        sql.append("    s.QUANTITY quantity, ");
        sql.append("    s.CREATED_DATE createdDate, ");
        sql.append("    s.PRICE price, ");
        sql.append("    p.NAME productName, ");
        sql.append("    e.NAME employeeName ");
        sql.append("FROM SALE s ");
        sql.append("LEFT JOIN PRODUCTS p ON s.PRODUCT_ID = p.ID ");
        sql.append("LEFT JOIN EMPLOYEE e ON s.EMPLOYEE_ID = e.ID ");
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
                .addScalar("code", StandardBasicTypes.STRING)
                .addScalar("name", StandardBasicTypes.STRING)
                .addScalar("phone", StandardBasicTypes.STRING)
                .addScalar("address", StandardBasicTypes.STRING)
                .addScalar("productId", StandardBasicTypes.LONG)
                .addScalar("employeeId", StandardBasicTypes.LONG)
                .addScalar("quantity", StandardBasicTypes.INTEGER)
                .addScalar("createdDate", StandardBasicTypes.LOCAL_DATE)
                .addScalar("price", StandardBasicTypes.BIG_DECIMAL)
                .addScalar("productName", StandardBasicTypes.STRING)
                .addScalar("employeeName", StandardBasicTypes.STRING)
                .setResultTransformer(Transformers.aliasToBean(BillDto.class));

        return query.getResultList();
    }

    @Override
    public Long countAll(String search) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM SALE s WHERE 1=1");

        if (!StringUtils.isEmpty(search)) {
            sql.append(" AND s.NAME LIKE :search");
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
