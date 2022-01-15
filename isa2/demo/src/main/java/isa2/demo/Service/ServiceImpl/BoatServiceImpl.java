package isa2.demo.Service.ServiceImpl;

import isa2.demo.DTO.FreeEntityDTO;
import isa2.demo.Exception.InvalidInputException;
import isa2.demo.Model.Adventure;
import isa2.demo.Model.Boat;
import isa2.demo.Model.Cottage;
import isa2.demo.Model.Reservation;
import isa2.demo.Repository.BoatRepository;
import isa2.demo.Repository.CottageRepository;
import isa2.demo.Service.BoatService;
import isa2.demo.Service.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class BoatServiceImpl implements BoatService {
    final
    BoatRepository boatRepository;

    final
    EntityService entityService;

    public BoatServiceImpl(BoatRepository boatRepository, EntityService entityService) {
        this.boatRepository = boatRepository;
        this.entityService = entityService;
    }

    @Override
    public List<Boat> findAll() {
        return this.boatRepository.findAll();
    }

    @Override
    public Boat findOne(Integer id) {
        return this.boatRepository.findById(id).get();
    }

    @Override
    public Collection<Boat> findFreeBoats(FreeEntityDTO request) throws InvalidInputException {
        if (request.getStartDate().isAfter(request.getEndDate()) || request.getStartDate().isEqual(request.getEndDate()))
            throw new InvalidInputException("Invalid start and end date");
        Collection<Boat> boats = boatRepository.findAll();
        Collection<Boat> freeBoats = new ArrayList<Boat>();
        if (request.getGrade() != null && request.getGrade() < 0)
            throw new InvalidInputException("Grade needs to be positive");
        if (request.getNumberOfGuests() != null && request.getNumberOfGuests() < 1)
            throw new InvalidInputException("Number of guests needs to be at least 1");
        for (Boat boat : boats) {
            if ((request.getNumberOfGuests() != null && boat.getMaxNumberOfGuests() < request.getNumberOfGuests()) || (boat.getAverageGrade() == null && request.getGrade() != null)  || (request.getGrade() != null && boat.getAverageGrade() < request.getGrade()))
                break;
            if (request.getCountry() != null && !request.getCountry().equals(""))
                if (!request.getCountry().equals(boat.getAddress().getCountry()))
                    break;
            if (request.getCity() != null && !request.getCity().equals(""))
                if (!request.getCity().equals(boat.getAddress().getCity()))
                    break;
            if (!entityService.isPeriodInRentalTime(boat, request.getStartDate(), request.getEndDate()))
                break;
            else
                freeBoats.add(boat);
            Collection<Reservation> reservations = boat.getReservations();
            for (Reservation reservation : reservations) {
                if (entityService.doTimeIntervalsIntersect(request.getStartDate(), request.getEndDate(), reservation.getReservedPeriod().getStartDate(), reservation.getReservedPeriod().getEndDate())) {
                    freeBoats.remove(boat);
                    break;
                }
            }
        }
        return freeBoats;
    }
}
