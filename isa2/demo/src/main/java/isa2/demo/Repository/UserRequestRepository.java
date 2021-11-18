package isa2.demo.Repository;

import isa2.demo.Model.User;
import isa2.demo.Model.UserRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRequestRepository extends JpaRepository<UserRequest,Integer>{
    //UserRequest findByVerificationCode(String verificationCode);
    @Query("SELECT u FROM UserRequest u WHERE u.verificationCode = ?1")
    UserRequest findByVerificationCode(String code);
}
