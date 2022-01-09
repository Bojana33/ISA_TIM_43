package isa2.demo.Service;

import isa2.demo.Model.Boat;
import java.util.List;

public interface BoatService {
    List<Boat> findAll();
    Boat findOne(Integer id);
    Boat addNewBoat(Boat boat);
    Boat deleteBoat(Integer boat_id);
}
