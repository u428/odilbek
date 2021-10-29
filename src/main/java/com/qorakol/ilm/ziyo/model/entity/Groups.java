package com.qorakol.ilm.ziyo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "groups")
@Data
public class Groups {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date begin;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date finish;

    @Column(name = "price")
    private double price;

    @Column(name = "subject_id")
    private Long subjectId;

    @Column(name = "lang_id")
    private Long languageId;

    @Column(name = "teacher_id")
    private Long teacherId;

    private String description;

    @Column(name = "image_id")
    private Long ImagesId;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id", insertable = false, updatable = false)
    private Teacher teacher;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subject_id", insertable = false, updatable = false)
    private Subjects subjects;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lang_id", insertable = false, updatable = false)
    private Language language;

    @OneToOne
    @JoinColumn(name = "image_id", insertable = false, updatable = false)
    private Images images;

}
