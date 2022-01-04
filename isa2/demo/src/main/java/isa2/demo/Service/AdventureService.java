package isa2.demo.Service;

import isa2.demo.DTO.AdventureDTO;
import isa2.demo.Model.Adventure;
import isa2.demo.Model.Owner;

import java.util.List;

public interface AdventureService {

    List<Adventure> findAll();

    Adventure findOne(Integer id);

    Adventure save(Adventure adventure);

    Adventure update(Integer id, Adventure adventure);

    void delete(Integer id);

    List<Adventure> findAdventuresByInstructor(Owner owner);
}
