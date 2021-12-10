package isa2.demo.Service;

import isa2.demo.DTO.CottageDTO;
import isa2.demo.Model.Cottage;

import java.util.List;

public interface CottageService {
    Cottage addNewCottage(Cottage cottage);
    List<Cottage> findAllCottages();
}
