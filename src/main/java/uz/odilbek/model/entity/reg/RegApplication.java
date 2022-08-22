package uz.odilbek.model.entity.reg;

import lombok.Data;
import uz.odilbek.constant.ApplicationStatus;
import uz.odilbek.constant.FacilityStatus;
import uz.odilbek.constant.FamilyStatus;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "sys_application")
public class RegApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_mame")
    private String middleName;

    @Column(name = "tel_number")
    private String telNumber;

    @Column(name = "faculty")
    private String faculty;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "course")
    private int course;

    @Column(name = "education_image")
    private Long educationImage;

    @Column(name = "grand")
    private boolean grand;

    @Column(name = "facility_status")
    @Enumerated(EnumType.STRING)
    private FacilityStatus facilityStatus;

    @Column(name = "facility_image")
    private Long facilityImage;

    @ElementCollection
    @Column(name = "disability_image")
    private List<Long> disabilityImage;

    @Column(name = "member")
    private int memberOfFamily;

    @Column(name = "family_status")
    @Enumerated(EnumType.STRING)
    private FamilyStatus familyStatus;

    @Column(name = "family_image")
    private Long familyImage;

    @Column(name = "persentage")
    private int persentage;

    @Column(name = "deleted")
    private boolean deleted = false;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;

    @Column(name = "created_by")
    private Long createdBy;


}
