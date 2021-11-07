package isa2.demo.DTO.Mappers;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.CottageDTO;
import isa2.demo.DTO.ReservationDTO;
import isa2.demo.Model.AdditionalService;
import isa2.demo.Model.Cottage;
import isa2.demo.Model.Reservation;
import net.bytebuddy.implementation.bytecode.Addition;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

@Component
public class CottageMapper {

    public final ModelMapperConfig modelMapper;

    public CottageMapper(ModelMapperConfig modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Cottage mapDtoToCottage(CottageDTO cottageDTO){
        Cottage cottage1 = modelMapper.modelMapper().map(cottageDTO, Cottage.class);
        Collection<Reservation> reservationCollection = cottage1.getReservations();
        for(Reservation reservation : reservationCollection){
            reservation.setEntity(cottage1);
            reservation.setCreationDate(LocalDateTime.now());
            for(AdditionalService additionalService: reservation.getAdditionalServices()){
                additionalService.setEntity(cottage1);
                additionalService.setReservation(reservation);
            }
        }

        cottage1.setReservations(reservationCollection);
        return cottage1;

    }
    public Collection<Reservation> mapCottageReservations(Collection<ReservationDTO> reservationDTOS){
        Collection<Reservation> reservations = new HashSet<>();
        for(ReservationDTO reservationDTO : reservationDTOS){
            reservations.add(modelMapper.modelMapper().map(reservationDTO, Reservation.class));
        }
        return reservations;
    }
}
