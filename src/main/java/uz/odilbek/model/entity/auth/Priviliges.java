package uz.odilbek.model.entity.auth;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "sys_priviliges")
@Data
public class Priviliges {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

//    @ManyToOne()
//    @JoinColumn(name = "role_id")
//    private Roles roles;
}
