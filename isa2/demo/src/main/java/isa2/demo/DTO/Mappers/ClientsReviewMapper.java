package isa2.demo.DTO.Mappers;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.ClientsReviewDTO;
import isa2.demo.DTO.ReservationDTO;
import isa2.demo.DTO.UserComplaintDTO;
import isa2.demo.Model.ClientsReview;
import isa2.demo.Model.Reservation;
import isa2.demo.Model.UserComplaint;
import isa2.demo.Repository.ReservationRepository;

public class ClientsReviewMapper {

    public final ModelMapperConfig modelMapper;

    public final ReservationRepository reservationRepository;

    public ClientsReviewMapper(ModelMapperConfig modelMapper, ReservationRepository reservationRepository){
        this.modelMapper = modelMapper;
        this.reservationRepository = reservationRepository;
    }

    public ClientsReview mapDtoToClientsReview(ClientsReviewDTO clientsReviewDTO){
        ClientsReview clientsReview = modelMapper.modelMapper().map(clientsReviewDTO, ClientsReview.class);
        Reservation reservation = clientsReview.getReservation();
        reservation.setClientsReview(clientsReview);
        return clientsReview;
    }

    public ClientsReviewDTO mapUserComplaintToDto(ClientsReview clientsReview){
        ClientsReviewDTO clientsReviewDTO = modelMapper.modelMapper().map(clientsReview,ClientsReviewDTO.class);
//        Reservation reservation = this.reservationRepository.findByUserComplaint(userComplaint);
        ReservationDTO reservationDTO = modelMapper.modelMapper().map(clientsReview.getReservation(), ReservationDTO.class);
        clientsReviewDTO.setReservationDTO(reservationDTO);
        return clientsReviewDTO;
    }
}
