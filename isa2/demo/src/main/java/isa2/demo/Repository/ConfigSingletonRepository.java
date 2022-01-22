package isa2.demo.Repository;

import isa2.demo.Model.ConfigSingleton;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfigSingletonRepository extends JpaRepository<ConfigSingleton,Integer> {
}
