package hib.bo;

import hib.dao.StockDao;
import hib.dao.StockDaoImpl;
import hib.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("stockBo")
public class StockBoImpl implements StockBo {

    @Autowired
    @Qualifier("stockDao")
    StockDao stockDao;

    public StockBoImpl() {
    }

//    @Autowired
//    public StockBoImpl(@Qualifier("stockDao") StockDao stockDao){
//        this.stockDao = stockDao;
//    }

    public void setStockDao(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    public void save(Stock stock) {
        stockDao.save(stock);
    }

    public void update(Stock stock) {
        stockDao.update(stock);
    }

    public void delete(Stock stock) {
        stockDao.delete(stock);
    }

    public Stock findByStockCode(String stockCode) {
        return stockDao.findByStockCode(stockCode);
    }
}
