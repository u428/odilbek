package uz.odilbek.model.entity.auth;

import lombok.Data;
import uz.odilbek.constant.RoleContants;

import javax.persistence.*;

@Entity
@Table(name = "sys_roles")
@Data
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Enumerated(EnumType.STRING)
    private RoleContants name;

    private int level;

//    @JsonIgnore
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roles")
//    private Set<Priviliges> priviliges;

}
