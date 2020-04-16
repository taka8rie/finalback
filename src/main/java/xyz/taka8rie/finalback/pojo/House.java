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
    int houseType;
    int houseArea;
    int houseStatus;
    Date lastupdateTime;
    BigDecimal soldPrice;//是否影响金钱精度尚未清楚。
    String addNote;
    String houseCover;
    int adminCheck;//0:未审核 1:已审核
    int isOrder;// 0:未被订购 1:已被订购
    String tenentClaim;//用户的评价


    public int getIsOrder() {
        return isOrder;
    }

    public void setIsOrder(int isOrder) {
        this.isOrder = isOrder;
    }

    public int getAdminCheck() {
        return adminCheck;
    }

    public void setAdminCheck(int adminCheck) {
        this.adminCheck = adminCheck;
    }

    public int getOwnerNumber() {
        return ownerNumber;
    }

    public void setOwnerNumber(int ownerNumber) {
        this.ownerNumber = ownerNumber;
    }

    public String getHouseCover() {
        return houseCover;
    }

    public void setHouseCover(String houseCover) {
        this.houseCover = houseCover;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getHouseAddr() {
        return houseAddr;
    }

    public void setHouseAddr(String houseAddr) {
        this.houseAddr = houseAddr;
    }

    public int getHouseType() {
        return houseType;
    }

    public void setHouseType(int houseType) {
        this.houseType = houseType;
    }

    public int getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(int houseArea) {
        this.houseArea = houseArea;
    }

    public int getHouseStatus() {
        return houseStatus;
    }

    public void setHouseStatus(int houseStatus) {
        this.houseStatus = houseStatus;
    }

    public Date getLastupdateTime() {
        return lastupdateTime;
    }

    public void setLastupdateTime(Date lastupdateTime) {
        this.lastupdateTime = lastupdateTime;
    }

    public BigDecimal getSoldPrice() {
        return soldPrice;
    }

    public void setSoldPrice(BigDecimal soldPrice) {
        this.soldPrice = soldPrice;
    }

    public String getAddNote() {
        return addNote;
    }

    public void setAddNote(String addNote) {
        this.addNote = addNote;
    }
}
