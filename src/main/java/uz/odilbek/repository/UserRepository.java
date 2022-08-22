package uz.odilbek.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.odilbek.model.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByLogin(String s);

    boolean existsByLogin(String login);
}
