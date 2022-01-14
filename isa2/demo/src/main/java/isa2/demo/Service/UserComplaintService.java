package isa2.demo.Service;

import isa2.demo.Model.Client;
import isa2.demo.Model.Entity;
import isa2.demo.Model.Reservation;
import isa2.demo.Model.UserComplaint;

import java.util.*;

public interface UserComplaintService {

    List<Reservation> createClientsList(Client client);

    UserComplaint save(UserComplaint userComplaint);

    UserComplaint update(UserComplaint userComplaint) throws Exception;

    List<UserComplaint> findAllUnprocessedComplaints();

    void sendResponse(UserComplaint userComplaint);
}
