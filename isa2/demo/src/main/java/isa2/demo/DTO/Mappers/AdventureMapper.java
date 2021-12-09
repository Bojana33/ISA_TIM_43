package isa2.demo.DTO.Mappers;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.AddressDTO;
import isa2.demo.DTO.AdventureDTO;
import isa2.demo.DTO.CottageDTO;
import isa2.demo.Model.Address;
import isa2.demo.Model.Adventure;
import isa2.demo.Model.Cottage;
import org.springframework.stereotype.Component;

@Component
public class AdventureMapper {

    public final ModelMapperConfig modelMapper;

    public AdventureMapper(ModelMapperConfig modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Adventure mapDtoToAdventure(AdventureDTO adventureDTO){
        Adventure adventure = modelMapper.modelMapper().map(adventureDTO, Adventure.class);
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
