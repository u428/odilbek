package uz.odilbek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.odilbek.constant.RoleContants;
import uz.odilbek.model.entity.auth.Roles;


@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {

    Roles findByName(RoleContants name);
}
