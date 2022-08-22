package uz.odilbek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.odilbek.constant.ApplicationStatus;
import uz.odilbek.model.entity.reg.RegApplication;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<RegApplication, Long> {

    List<RegApplication> findByCreatedByAndDeletedIsFalse(Long id);

    RegApplication findByCreatedByAndApplicationStatusIsNotAndDeletedIsFalse(Long id, ApplicationStatus applicationStatus);

}
