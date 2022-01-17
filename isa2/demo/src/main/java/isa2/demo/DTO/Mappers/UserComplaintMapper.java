package isa2.demo.DTO.Mappers;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.ReservationDTO;
import isa2.demo.DTO.UserComplaintDTO;
import isa2.demo.Model.Reservation;
import isa2.demo.Model.UserComplaint;
import isa2.demo.Repository.ReservationRepository;
import org.springframework.stereotype.Component;

@Component
public class UserComplaintMapper {

    public final ModelMapperConfig modelMapper;

    public final ReservationRepository reservationRepository;

    public UserComplaintMapper(ModelMapperConfig modelMapper, ReservationRepository reservationRepository){
        this.modelMapper = modelMapper;
        this.reservationRepository = reservationRepository;
    }

    public UserComplaint mapDtoToUserComplaint(UserComplaintDTO userComplaintDTO){
        UserComplaint userComplaint = modelMapper.modelMapper().map(userComplaintDTO,UserComplaint.class);
        if (userComplaint.getReservation() != null) {
            Reservation reservation = userComplaint.getReservation();
            reservation.setUserComplaint(userComplaint);
            //this.reservationRepository.save(reservation);
        }
        return userComplaint;
    }

    public UserComplaintDTO mapUserComplaintToDto(UserComplaint userComplaint){
        UserComplaintDTO userComplaintDTO = modelMapper.modelMapper().map(userComplaint,UserComplaintDTO.class);
//        Reservation reservation = this.reservationRepository.findByUserComplaint(userComplaint);
        ReservationDTO reservationDTO = modelMapper.modelMapper().map(userComplaint.getReservation(), ReservationDTO.class);
        userComplaintDTO.setReservationDTO(reservationDTO);
        return userComplaintDTO;
    }
}
