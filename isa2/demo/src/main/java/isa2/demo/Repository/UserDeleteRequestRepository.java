package isa2.demo.Repository;

import isa2.demo.Model.User;
import isa2.demo.Model.UserDeleteRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDeleteRequestRepository extends JpaRepository<UserDeleteRequest,Integer> {

    UserDeleteRequest findByUser(User user);

}
