package isa2.demo.Repository;

import isa2.demo.Model.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeriodRepository extends JpaRepository<Period,Integer> {

}
