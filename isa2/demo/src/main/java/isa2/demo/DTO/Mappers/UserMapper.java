package isa2.demo.DTO.Mappers;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.UserDTO;
import isa2.demo.Model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public final ModelMapperConfig modelMapper;

    public UserMapper(ModelMapperConfig modelMapper) {
        this.modelMapper = modelMapper;
    }

    public User mapDtoToUser(UserDTO userDTO){
        User user = modelMapper.modelMapper().map(userDTO, User.class);
        return user;
    }
    public UserDTO mapUserToDto(User user){
        UserDTO userDTO = modelMapper.modelMapper().map(user, UserDTO.class);
        return userDTO;
    }
}
