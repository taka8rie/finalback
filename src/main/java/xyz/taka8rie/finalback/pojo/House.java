package xyz.taka8rie.finalback.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "houseinfo")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class House  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "house_number")
    int houseNumber;//是否需要改为house_number?
    String houseAddr;
    int ownerNumber;//添加了房屋对应的房主账号
    String houseType;
    int houseArea;
    String houseStatus;//由int改为String 4.16
    Date lastupdateTime;
    BigDecimal soldPrice;//是否影响金钱精度尚未清楚。
    String addNote;
    String houseCover;
    int adminCheck;//0:未审核 1:已审核 由int改为String 4.16
    int isOrder;// 0:未被订购 1:已被订购
    String tenentClaim;//用户的评价

}
