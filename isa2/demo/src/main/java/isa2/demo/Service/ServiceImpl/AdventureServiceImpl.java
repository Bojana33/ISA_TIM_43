package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.Adventure;
import isa2.demo.Repository.AdventureRepository;
import isa2.demo.Service.AdventureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdventureServiceImpl implements AdventureService {

    @Autowired
    private AdventureRepository adventureRepository;


    @Override
    public List<Adventure> findAll() {
        return this.adventureRepository.findAll();
    }

    @Override
    public Adventure findOne(Integer id) {
        return this.adventureRepository.findById(id).get();
    }

    @Override
    public Adventure save(Adventure adventure) {
        // TODO: Sacuvati ulogovanog instruktora
        return this.adventureRepository.save(adventure);
    }

    @Override
    public Adventure update(Integer id, Adventure adventure) {
        Adventure adventureForUpdate = this.adventureRepository.findById(adventure.getId()).get();
        adventureForUpdate.setName(adventure.getName());
        adventureForUpdate.setAddress(adventure.getAddress());
        adventureForUpdate.setCancellationFee(adventure.getCancellationFee());
        adventureForUpdate.setInstructor(adventure.getInstructor());
        adventureForUpdate.setHouseRules(adventure.getHouseRules());
        adventureForUpdate.setInstructorBio(adventure.getInstructorBio());
        adventureForUpdate.setDefaultFishingEquipment(adventure.getDefaultFishingEquipment());
        adventureForUpdate.setAdditionalServices(adventure.getAdditionalServices());
        adventureForUpdate.setDescription(adventure.getDescription());
        adventureForUpdate.setEntityPhoto(adventure.getEntityPhoto());
        adventureForUpdate.setPhotos(adventure.getPhotos());
        adventureForUpdate.setPricePerDay(adventure.getPricePerDay());
        adventureForUpdate.setRentalTimes(adventure.getRentalTimes());
        adventureForUpdate.setSubscribedClients(adventure.getSubscribedClients());
        adventureForUpdate.setAdditionalServices(adventure.getAdditionalServices());
        adventureForUpdate.setReservations(adventure.getReservations());
        adventureForUpdate.setAverageGrade(adventure.getAverageGrade());
        adventureForUpdate.setMaxNumberOfGuests(adventure.getMaxNumberOfGuests());

        return this.adventureRepository.save(adventureForUpdate);
    }

    @Override
    public void delete(Integer id) {
        this.adventureRepository.deleteById(id);
    }
}
