package isa2.demo.Service;

import isa2.demo.DTO.FreeEntityDTO;
import isa2.demo.Exception.InvalidInputException;
import isa2.demo.Model.Cottage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CottageService {
    Cottage addNewCottage(Cottage cottage);
    Cottage updateCottage(Cottage cottage);
    Optional<Cottage> findById(Integer id);
    List<Cottage> findAllCottages();
    List<Cottage> findCottagesByName(String name);
    Cottage deleteCottage(Integer id) throws Exception;
    List<Cottage> findAll();
    Collection<Cottage> findFreeCottages(FreeEntityDTO request) throws InvalidInputException;
    ArrayList<Cottage> sortCottages(Collection<Cottage> cottages, Integer criterion, boolean asc);
}
