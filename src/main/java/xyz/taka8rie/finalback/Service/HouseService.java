package xyz.taka8rie.finalback.Service;

import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xyz.taka8rie.finalback.dao.HouseDAO;
import xyz.taka8rie.finalback.pojo.House;

import java.util.List;

@Service
public class HouseService  {
    @Autowired
    HouseDAO houseDAO;

    public List<House> list() {
        return houseDAO.findAll();
    }





}
