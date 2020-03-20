package xyz.taka8rie.finalback.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.taka8rie.finalback.dao.DealDAO;
import xyz.taka8rie.finalback.pojo.Deal;

@Service
public class DealService {
    @Autowired
    DealDAO dealDAO;

    public void addOrUpdate(Deal deal) {
        dealDAO.save(deal);
    }

}
