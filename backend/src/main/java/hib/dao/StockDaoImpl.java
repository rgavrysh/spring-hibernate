package hib.dao;

import hib.model.Stock;
import hib.util.CustomHibernateDaoSupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository("stockDao")
public class StockDaoImpl extends CustomHibernateDaoSupport implements StockDao {

    @PersistenceContext
    EntityManager entityManager;

    public void save(Stock stock) {
        entityManager.persist(stock);
    }

    public void update(Stock stock) {
        entityManager.merge(stock);
    }

    public void delete(Stock stock) {
        entityManager.detach(stock);
    }

    public Stock findByStockCode(String stockCode) {
        Query query = entityManager.createNativeQuery("select * from stock s where s.stock_code=?", Stock.class);
        query.setParameter(1, stockCode);
        Stock stock = (Stock) query.getSingleResult();
        return stock;
    }
}
