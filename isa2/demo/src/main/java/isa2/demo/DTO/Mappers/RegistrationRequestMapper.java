package isa2.demo.DTO.Mappers;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.RegistrationRequestDTO;
import isa2.demo.DTO.UserRequestDTO;
import isa2.demo.Model.RegistrationRequest;
import isa2.demo.Model.UserRequest;
import org.springframework.stereotype.Component;

@Component
public class RegistrationRequestMapper {
    public final ModelMapperConfig modelMapper;

    public RegistrationRequestMapper(ModelMapperConfig modelMapper) {
        this.modelMapper = modelMapper;
    }
    public RegistrationRequest mapDtoToRegistration(RegistrationRequestDTO registrationRequestDTO){
        RegistrationRequest registrationRequest = modelMapper.modelMapper().map(registrationRequestDTO, RegistrationRequest.class);
        return registrationRequest;
    }
    public RegistrationRequestDTO mapRegistrationRequestToDto(RegistrationRequest registrationRequest){
        RegistrationRequestDTO registrationRequestDTO = modelMapper.modelMapper().map(registrationRequest, RegistrationRequestDTO.class);
        return registrationRequestDTO;
    }
}
