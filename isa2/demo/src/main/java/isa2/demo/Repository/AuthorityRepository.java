package isa2.demo.Repository;

import isa2.demo.Model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer> {
    Authority findByName(String name);
    Optional<Authority> findById(Integer id);
}
