package com.qorakol.ilm.ziyo.repository;

import com.qorakol.ilm.ziyo.model.entity.AuthEntity;
import com.qorakol.ilm.ziyo.model.entity.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Teacher findByAuthEntity(AuthEntity authEntity);

    Page<Teacher> findAllByDeleteIsFalse(Pageable pageable);

    List<Teacher> findAllByDeleteIsFalse();

    Optional<Teacher> findByIdAndDeleteIsFalse(Long id);
}
