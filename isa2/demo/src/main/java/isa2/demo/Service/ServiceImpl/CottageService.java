package isa2.demo.Service.ServiceImpl;

import isa2.demo.DTO.CottageDTO;
import isa2.demo.Model.Cottage;
import isa2.demo.Repository.CottageRepository;
import isa2.demo.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CottageService implements isa2.demo.Service.CottageService {

    final
    CottageRepository cottageRepository;

    final
    UserRepository userRepository;

    public CottageService(CottageRepository cottageRepository, UserRepository userRepository) {
        this.cottageRepository = cottageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Cottage addNewCottage(CottageDTO cottageDTO) {

        Cottage cottage = new Cottage();
        cottage.setName(cottageDTO.getCottageName());
        cottage.setAddress(cottageDTO.getAddress());
        cottage.setMaxNumberOfGuests(cottageDTO.getMaxNumberOfGuests());
        cottage.setPricePerDay(cottageDTO.getPricePerDay());
        cottage.setRentalTimes(null);
        cottage.setReservations(null);
        cottage.setSubscribedClients(null);
        cottage.setDescription(cottageDTO.getDescription());
        cottage.setAverageGrade((double) 0);
        cottage.setAdditionalServices(null);
        cottage.setCottageOwner(cottage.getCottageOwner());
        //TODO: test sa puno nullova, ispraviti ovo
        cottage.setRooms(null);

        return cottageRepository.save(cottage);
    }
}
