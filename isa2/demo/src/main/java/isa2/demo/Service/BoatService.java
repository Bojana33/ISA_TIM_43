package isa2.demo.Service;

import isa2.demo.DTO.FreeEntityDTO;
import isa2.demo.Exception.InvalidInputException;
import isa2.demo.Model.Boat;

import java.util.Collection;
import java.util.List;

public interface BoatService {
    List<Boat> findAll();
    Boat findOne(Integer id);
    List<Boat> findBoatsByOwner(Owner owner);
    Boat addNewBoat(Boat boat);
    Boat deleteBoat(Integer boat_id);
    Boat updateBoat(Boat boat);
    Collection<Boat> findFreeBoats(FreeEntityDTO request) throws InvalidInputException;
}
