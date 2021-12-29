package isa2.demo.Service;

import isa2.demo.Model.Cottage;

import java.util.List;
import java.util.Optional;

public interface CottageService {
    Cottage addNewCottage(Cottage cottage);
    Cottage updateCottage(Cottage cottage);
    Optional<Cottage> findById(Integer id);
    List<Cottage> findAllCottages();
    List<Cottage> findCottagesByName(String name);
    Cottage deleteCottage(Integer id) throws Exception;
}
