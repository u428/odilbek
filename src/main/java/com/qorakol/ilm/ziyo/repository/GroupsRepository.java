package com.qorakol.ilm.ziyo.repository;

import com.qorakol.ilm.ziyo.model.entity.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupsRepository extends JpaRepository<Groups, Long> {
}
