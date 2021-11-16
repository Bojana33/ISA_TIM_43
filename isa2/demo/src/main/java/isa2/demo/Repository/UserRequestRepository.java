package isa2.demo.Repository;

import isa2.demo.Model.User;
import isa2.demo.Model.UserRequest;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRequestRepository extends JpaRepository<UserRequest,Integer>{
}
