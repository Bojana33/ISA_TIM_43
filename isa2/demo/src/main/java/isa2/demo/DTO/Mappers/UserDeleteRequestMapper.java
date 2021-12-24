package isa2.demo.DTO.Mappers;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.UserDeleteRequestDTO;
import isa2.demo.Model.User;
import isa2.demo.Model.UserDeleteRequest;
import isa2.demo.Service.ServiceImpl.UserServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class UserDeleteRequestMapper {

    public final ModelMapperConfig modelMapper;

    public final UserServiceImpl userService;

    public UserDeleteRequestMapper(ModelMapperConfig modelMapper, UserServiceImpl userService){
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    public UserDeleteRequest mapDtoToUserDeleteRequest(UserDeleteRequestDTO userDeleteRequestDTO){
        UserDeleteRequest userDeleteRequest = modelMapper.modelMapper().map(userDeleteRequestDTO, UserDeleteRequest.class);
        User user = this.userService.findById(userDeleteRequestDTO.getUserId());
        userDeleteRequest.setUser(user);
        user.setUserDeleteRequest(userDeleteRequest);
        return userDeleteRequest;
    }

    public UserDeleteRequestDTO mapUserDeleteRequestToDto(UserDeleteRequest userDeleteRequest){
        UserDeleteRequestDTO userDeleteRequestDTO = modelMapper.modelMapper().map(userDeleteRequest,UserDeleteRequestDTO.class);
        userDeleteRequestDTO.setUserId(userDeleteRequest.getUser().getId());
        return userDeleteRequestDTO;
    }
}
