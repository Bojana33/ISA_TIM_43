package isa2.demo.Repository;

import isa2.demo.Model.UserComplaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserComplaintRepository extends JpaRepository<UserComplaint, Integer> {

}
