package isa2.demo.Service;

import isa2.demo.DTO.AdventureDTO;
import isa2.demo.DTO.FreeEntityDTO;
import isa2.demo.Exception.InvalidInputException;
import isa2.demo.Model.Adventure;
import isa2.demo.Model.Owner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface AdventureService {

    List<Adventure> findAll();

    Optional<Adventure> findOne(Integer id);

    Adventure save(Adventure adventure);

    Adventure update(Adventure adventure);

    void delete(Integer id);

    List<Adventure> findAdventuresByInstructor(Owner owner);

    Collection<Adventure> findFreeAdventures(FreeEntityDTO request) throws InvalidInputException;

    ArrayList<AdventureDTO> sortAdventures(Collection<AdventureDTO> adventures, String criterion, boolean asc);

    Owner getOwnerForAdventure(Integer adventureId);
}
