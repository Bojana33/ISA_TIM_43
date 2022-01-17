package isa2.demo.Repository;

import isa2.demo.Model.UserComplaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface UserComplaintRepository extends JpaRepository<UserComplaint, Integer> {

    List<UserComplaint> findAllByProcessedIsFalse();
}
