package isa2.demo.Service.ServiceImpl;

import isa2.demo.DTO.FreeEntityDTO;
import isa2.demo.Exception.InvalidInputException;
import isa2.demo.Model.Adventure;
import isa2.demo.Model.Boat;
import isa2.demo.Model.Reservation;
import isa2.demo.Repository.AdventureRepository;
import isa2.demo.Repository.BoatRepository;
import isa2.demo.Service.AdventureService;
import isa2.demo.Service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AdventureServiceImpl implements AdventureService {

    final
    AdventureRepository adventureRepository;

    final
    EntityService entityService;

    public AdventureServiceImpl(AdventureRepository adventureRepository, EntityService entityService) {
        this.adventureRepository = adventureRepository;
        this.entityService = entityService;
    }

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
        return this.adventureRepository.save(adventure);
    }

    @Override
    public Adventure update(Integer id, Adventure adventure) {
        Adventure adventureForUpdate = this.adventureRepository.findById(adventure.getId()).get();
        adventureForUpdate.setName(adventure.getName());
        adventureForUpdate.setAddress(adventure.getAddress());
        adventureForUpdate.setCancellationFee(adventure.getCancellationFee());
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

    @Override
    public Collection<Adventure> findFreeAdventures(FreeEntityDTO request) throws InvalidInputException {
        if (request.getStartDate().isAfter(request.getEndDate()) || request.getStartDate().isEqual(request.getEndDate()))
            throw new InvalidInputException("Invalid start and end date");
        Collection<Adventure> adventures = adventureRepository.findAll();
        Collection<Adventure> freeAdventures = new ArrayList<Adventure>();
        if (request.getGrade() != null && request.getGrade() < 0)
            throw new InvalidInputException("Grade needs to be positive");
        if (request.getNumberOfGuests() != null && request.getNumberOfGuests() < 1)
            throw new InvalidInputException("Number of guests needs to be at least 1");
        for (Adventure adventure : adventures) {
            if ((request.getNumberOfGuests() != null && adventure.getMaxNumberOfGuests() < request.getNumberOfGuests()) || (adventure.getAverageGrade() == null && request.getGrade() != null)  || (request.getGrade() != null && adventure.getAverageGrade() < request.getGrade()))
                break;
            if (request.getCountry() != null && !request.getCountry().equals(""))
                if (!request.getCountry().equals(adventure.getAddress().getCountry()))
                    break;
            if (request.getCity() != null && !request.getCity().equals(""))
                if (!request.getCity().equals(adventure.getAddress().getCity()))
                    break;
            if (!entityService.isPeriodInRentalTime(adventure, request.getStartDate(), request.getEndDate()))
                break;
            else
                freeAdventures.add(adventure);
            Collection<Reservation> reservations = adventure.getReservations();
            for (Reservation reservation : reservations) {
                if (entityService.doTimeIntervalsIntersect(request.getStartDate(), request.getEndDate(), reservation.getReservedPeriod().getStartDate(), reservation.getReservedPeriod().getEndDate())) {
                    freeAdventures.remove(adventure);
                    break;
                }
            }
        }
        return freeAdventures;
    }
}
