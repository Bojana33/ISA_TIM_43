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
    public Cottage addNewCottage(Cottage cottage) {
        //TODO: uvezati cottageOwnera(Ulogovani user) sa vikendicom
        return cottageRepository.save(cottage);
    }
}
