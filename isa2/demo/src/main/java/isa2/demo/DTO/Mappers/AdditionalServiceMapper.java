package isa2.demo.DTO.Mappers;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.AdditionalServiceDTO;
import isa2.demo.Model.AdditionalService;
import isa2.demo.Model.Address;
import isa2.demo.Model.Owner;
import org.springframework.stereotype.Component;

@Component
public class AdditionalServiceMapper {
    public final ModelMapperConfig modelMapper;

    public AdditionalServiceMapper(ModelMapperConfig modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AdditionalService mapDtoToAdditionalService(AdditionalServiceDTO additionalServiceDTO){
        AdditionalService additionalService = modelMapper.modelMapper().map(additionalServiceDTO, AdditionalService.class);
        return additionalService;
    }

    public AdditionalServiceDTO mapAdditionalServiceToDTO(AdditionalService additionalService){
        AdditionalServiceDTO additionalServiceDTO = modelMapper.modelMapper().map(additionalService, AdditionalServiceDTO.class);
        return additionalServiceDTO;
    }
}
