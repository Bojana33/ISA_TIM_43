package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.Adventure;
import isa2.demo.Model.Boat;
import isa2.demo.Model.Owner;
import isa2.demo.Repository.BoatRepository;
import isa2.demo.Service.BoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoatServiceImpl implements BoatService {
    @Autowired
    BoatRepository boatRepository;

    @Override
    public List<Boat> findAll() {
        return this.boatRepository.findAll();
    }

    @Override
    public Boat findOne(Integer id) {
        return this.boatRepository.findById(id).get();
    }

    @Override
    public List<Boat> findBoatsByOwner(Owner owner) {
        return this.boatRepository.findAllByOwner(owner);
    }
}
