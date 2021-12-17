package isa2.demo.DTO.Mappers;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.CottageDTO;
import isa2.demo.DTO.UserRequestDTO;
import isa2.demo.Model.Cottage;
import isa2.demo.Model.UserRequest;
import org.springframework.stereotype.Component;

@Component
public class UserRequestMapper {
    public final ModelMapperConfig modelMapper;

    public UserRequestMapper(ModelMapperConfig modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserRequest mapDtoToUserRequest(UserRequestDTO userRequestDTO){
        UserRequest userRequest = modelMapper.modelMapper().map(userRequestDTO, UserRequest.class);
        return userRequest;
    }
    public UserRequestDTO mapUserRequestToDto(UserRequest userRequest){
        UserRequestDTO userRequestDTO = modelMapper.modelMapper().map(userRequest, UserRequestDTO.class);
        return userRequestDTO;
    }
}
