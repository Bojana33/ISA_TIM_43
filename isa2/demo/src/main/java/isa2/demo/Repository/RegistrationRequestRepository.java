package isa2.demo.Repository;

import isa2.demo.Model.RegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest,Integer> {
    @Override
    List<RegistrationRequest> findAll();

    List<RegistrationRequest> findAllByConfirmed(Boolean confirmed);
}
