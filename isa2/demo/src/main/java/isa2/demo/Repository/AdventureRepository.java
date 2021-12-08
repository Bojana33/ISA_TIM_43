package isa2.demo.Repository;

import isa2.demo.Model.Adventure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdventureRepository extends JpaRepository<Adventure,Integer> {

    List<Adventure> findAll();
}
