package uz.odilbek.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import uz.odilbek.model.entity.auth.Roles;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sys_user")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "tel_number")
    private String telNumber;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private String password;

    @Column(name = "role_id", nullable = false)
    @JsonIgnore
    private Long rolesId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", nullable = false, insertable = false, updatable = false)
    @JsonIgnore
    private Roles roles;

}
