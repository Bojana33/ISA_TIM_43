package isa2.demo.Service;

import isa2.demo.Model.Cottage;

import java.time.LocalDateTime;
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
    Collection<Cottage> findFreeCottages(LocalDateTime startDate, LocalDateTime endDate);
}
