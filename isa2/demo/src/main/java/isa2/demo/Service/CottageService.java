package isa2.demo.Service;

import isa2.demo.DTO.CottageDTO;
import isa2.demo.Model.Cottage;

import java.util.List;

public interface CottageService {
    Cottage addNewCottage(Cottage cottage);
    Cottage updateCottage(Cottage cottage);
    Cottage findById(Integer id);
    List<Cottage> findAllCottages();
    void deleteCottage(Integer id);
}
