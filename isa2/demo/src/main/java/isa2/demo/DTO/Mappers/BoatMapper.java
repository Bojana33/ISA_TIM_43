package isa2.demo.DTO.Mappers;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.AddressDTO;
import isa2.demo.DTO.AdventureDTO;
import isa2.demo.DTO.BoatDTO;
import isa2.demo.Model.Address;
import isa2.demo.Model.Adventure;
import isa2.demo.Model.Boat;
import isa2.demo.Model.Owner;
import isa2.demo.Service.ServiceImpl.OwnerServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class BoatMapper {

    public final ModelMapperConfig modelMapper;
    public final OwnerServiceImpl ownerService;

    public BoatMapper(ModelMapperConfig modelMapper, OwnerServiceImpl ownerService) {
        this.modelMapper = modelMapper;
        this.ownerService = ownerService;
    }

    public Boat mapDtoToBoat(BoatDTO boatDTO){
        Boat boat = modelMapper.modelMapper().map(boatDTO, Boat.class);
        //Owner owner = ownerService.findById(boatDTO.getBoatOwnerId());
        //boat.setOwner(owner);
//        Collection<Adventure> adventures = owner.getAdventures();
//        adventures.add(adventure);
//        owner.setAdventures(adventures);
        Address address = boat.getAddress();
        address.setEntity(boat);
        return boat;
    }

    public BoatDTO mapBoatToDTO(Boat boat){
        BoatDTO boatDTO = modelMapper.modelMapper().map(boat, BoatDTO.class);
        AddressDTO addressDTO = modelMapper.modelMapper().map(boat.getAddress(),AddressDTO.class);
        boatDTO.setAddress(addressDTO);
        return boatDTO;
    }
}