package xyz.taka8rie.finalback.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@ToString
@Table(name = "admin_login_role")
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer"})
public class AdminLoginRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;
    int lid;
    int rid;
}
