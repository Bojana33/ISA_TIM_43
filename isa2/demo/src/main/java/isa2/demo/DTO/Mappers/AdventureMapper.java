package isa2.demo.DTO.Mappers;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.AddressDTO;
import isa2.demo.DTO.AdventureDTO;
import isa2.demo.DTO.CottageDTO;
import isa2.demo.Model.*;
import isa2.demo.Service.OwnerService;
import isa2.demo.Service.ServiceImpl.OwnerServiceImpl;
import isa2.demo.Service.ServiceImpl.UserServiceImpl;
import isa2.demo.Service.UserService;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class AdventureMapper {

    public final ModelMapperConfig modelMapper;
    public final OwnerServiceImpl ownerService;

    public AdventureMapper(ModelMapperConfig modelMapper, OwnerServiceImpl ownerService) {
        this.modelMapper = modelMapper;
        this.ownerService = ownerService;
    }

    public Adventure mapDtoToAdventure(AdventureDTO adventureDTO){
        Adventure adventure = modelMapper.modelMapper().map(adventureDTO, Adventure.class);
        Owner owner = ownerService.findById(adventureDTO.getAdventureOwnerId());
        adventure.setOwner(owner);
//        Collection<Adventure> adventures = owner.getAdventures();
//        adventures.add(adventure);
//        owner.setAdventures(adventures);
        Address address = adventure.getAddress();
        address.setEntity(adventure);
        return adventure;
    }

    public AdventureDTO mapAdventureToDTO(Adventure adventure){
        AdventureDTO adventureDTO = modelMapper.modelMapper().map(adventure, AdventureDTO.class);
        AddressDTO addressDTO = modelMapper.modelMapper().map(adventure.getAddress(),AddressDTO.class);
        adventureDTO.setAddressDTO(addressDTO);
        return adventureDTO;
    }
}
