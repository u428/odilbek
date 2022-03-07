package com.qorakol.ilm.ziyo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "teacher")
@Data
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    private int gender;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "tel_nomer")
    private String telNomer;

    @Column(name = "tg_link")
    private String tgLink;

    @Column(name = "in_link")
    private String inLink;

    @Column(name = "fe_link")
    private String fLink;

    @Column(name = "gmail")
    private String gmail;

    private String description;

    @JsonIgnore
    @Column(name = "auth_id", unique = false)
    private Long authId;

    @Column(name = "image_id", unique = false)
    private Long imagesId;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "auth_id", updatable = false, insertable = false)
    private AuthEntity authEntity;

    @ManyToMany()
    @JoinTable(name = "teacher_languages",
            joinColumns = {@JoinColumn(name = "teacher_id")},
            inverseJoinColumns = {@JoinColumn(name = "lang_id")})
    private List<Language> languages;

    @ManyToMany()
    @JoinTable(name = "teacher_subject",
            joinColumns = {@JoinColumn(name = "teacher_id")},
            inverseJoinColumns = {@JoinColumn(name = "subject_id")})
    private List<Subjects> subjects;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "image_id", insertable = false, updatable = false)
    private Images images;

    @JsonIgnore
    private boolean delete = false;



}
