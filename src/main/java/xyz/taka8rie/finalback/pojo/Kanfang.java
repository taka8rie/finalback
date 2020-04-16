package xyz.taka8rie.finalback.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name="scoreinfo")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class Kanfang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="show_number")
    int showNumber;
    Date seeTime;
    int houseNumber;
    int tenantNumber;
}
