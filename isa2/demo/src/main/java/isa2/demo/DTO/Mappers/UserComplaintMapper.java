package isa2.demo.DTO.Mappers;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.UserComplaintDTO;
import isa2.demo.Model.UserComplaint;
import org.springframework.stereotype.Component;

@Component
public class UserComplaintMapper {

    public final ModelMapperConfig modelMapper;

    public UserComplaintMapper(ModelMapperConfig modelMapper){
        this.modelMapper = modelMapper;
    }

    public UserComplaint mapDtoToUserComplaint(UserComplaintDTO userComplaintDTO){
        UserComplaint userComplaint = modelMapper.modelMapper().map(userComplaintDTO,UserComplaint.class);
        return userComplaint;
    }

    public UserComplaintDTO mapUserComplaintToDto(UserComplaint userComplaint){
        UserComplaintDTO userComplaintDTO = modelMapper.modelMapper().map(userComplaint,UserComplaintDTO.class);
        return userComplaintDTO;
    }
}
