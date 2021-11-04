package isa2.demo.Service;

import isa2.demo.DTO.CottageDTO;
import isa2.demo.Model.Cottage;

public interface CottageService {
    Cottage addNewCottage(CottageDTO cottageDTO);
}
