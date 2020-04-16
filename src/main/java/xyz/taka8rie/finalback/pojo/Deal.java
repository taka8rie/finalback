package xyz.taka8rie.finalback.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Data
@Entity
@Table(name="deal")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Deal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="deal_number")
    int dealNumber;
    int houseNumber;
    int ownerNumber;
    int tenantNumber;
    Date beginTime;
    Date endTime;
    String claim;
    BigDecimal price;
    int staffNumber;
    Date handleTime;

    public int getDealNumber() {
        return dealNumber;
    }

    public void setDealNumber(int dealNumber) {
        this.dealNumber = dealNumber;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getOwnerNumber() {
        return ownerNumber;
    }

    public void setOwnerNumber(int ownerNumber) {
        this.ownerNumber = ownerNumber;
    }

    public int getTenantNumber() {
        return tenantNumber;
    }

    public void setTenantNumber(int tenantNumber) {
        this.tenantNumber = tenantNumber;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getClaim() {
        return claim;
    }

    public void setClaim(String claim) {
        this.claim = claim;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(int staffNumber) {
        this.staffNumber = staffNumber;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }
}
