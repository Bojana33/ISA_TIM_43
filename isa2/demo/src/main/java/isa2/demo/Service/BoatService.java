package isa2.demo.Service;

import isa2.demo.Model.Boat;
import isa2.demo.Model.Cottage;
import isa2.demo.Model.Owner;

import java.util.List;

public interface BoatService {
    List<Boat> findAll();
    Boat findOne(Integer id);
    List<Boat> findBoatsByOwner(Owner owner);
}
