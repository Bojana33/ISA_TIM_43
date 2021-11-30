package isa2.demo.Repository;

import isa2.demo.Model.RegistrationRequest;
import isa2.demo.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRequestRepository extends JpaRepository<RegistrationRequest,Integer> {
    @Override
    List<RegistrationRequest> findAll();

    List<RegistrationRequest> findAllByConfirmed(Boolean confirmed);

    RegistrationRequest findByEmail(String email);
}
