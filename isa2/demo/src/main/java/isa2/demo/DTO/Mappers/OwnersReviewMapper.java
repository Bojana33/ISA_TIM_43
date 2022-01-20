package isa2.demo.DTO.Mappers;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.OwnersReviewDTO;
import isa2.demo.DTO.ReservationDTO;
import isa2.demo.Model.OwnersReview;
import isa2.demo.Model.Reservation;
import isa2.demo.Repository.ReservationRepository;
import org.springframework.stereotype.Component;

@Component
public class OwnersReviewMapper {

    public final ModelMapperConfig modelMapper;

    public final ReservationRepository reservationRepository;

    public OwnersReviewMapper(ModelMapperConfig modelMapper, ReservationRepository reservationRepository){
        this.modelMapper = modelMapper;
        this.reservationRepository = reservationRepository;
    }

    public OwnersReview mapDtoToOwnersReview(OwnersReviewDTO ownersReviewDTO){
        OwnersReview ownersReview = modelMapper.modelMapper().map(ownersReviewDTO, OwnersReview.class);
        Reservation reservation = ownersReview.getReservation();
        reservation.setOwnersReview(ownersReview);
        return ownersReview;
    }

    public OwnersReviewDTO mapOwnersReviewToDto(OwnersReview ownersReview){
        OwnersReviewDTO ownersReviewDTO = modelMapper.modelMapper().map(ownersReview,OwnersReviewDTO.class);
        ReservationDTO reservationDTO = modelMapper.modelMapper().map(ownersReview.getReservation(), ReservationDTO.class);
        ownersReviewDTO.setReservationDTO(reservationDTO);
        return ownersReviewDTO;
    }
}
