package isa2.demo.Service.ServiceImpl;

import isa2.demo.Model.Cottage;
import isa2.demo.Repository.CottageRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CottageServiceImpl implements isa2.demo.Service.CottageService {

    final
    CottageRepository cottageRepository;


    public CottageServiceImpl(CottageRepository cottageRepository) {
        this.cottageRepository = cottageRepository;
    }

    @Override
    public Cottage addNewCottage(Cottage cottage) {
        //TODO: uvezati cottageOwnera(Ulogovani user) sa vikendicom
        cottage.setSubscribedClients(Collections.EMPTY_LIST);
        return cottageRepository.save(cottage);

    }

    @Override
    public Cottage updateCottage(Cottage cottage) {
        return cottageRepository.save(cottage);
    }

    @Override
    public List<Cottage> findAllCottages() {
        return cottageRepository.findAll();
    }

    @Override
    public Cottage deleteCottage(Integer id) throws Exception{
        Cottage cottage = cottageRepository.findByIdAndReservationsIsNull(id);
        if(cottage != null){
            cottageRepository.deleteById(id);
        }else{
            throw new Exception("Cottage doesn't exist");
        }
        return cottage;
    }

    @Override
    public Optional<Cottage> findById(Integer id) {
        return cottageRepository.findById(id);
    }
}
