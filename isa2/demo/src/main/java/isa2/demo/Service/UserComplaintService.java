package isa2.demo.Service;

import isa2.demo.Model.Client;
import isa2.demo.Model.Entity;
import isa2.demo.Model.Reservation;

import java.util.*;

public interface UserComplaintService {

    List<Reservation> createClientsList(Client client);
}
