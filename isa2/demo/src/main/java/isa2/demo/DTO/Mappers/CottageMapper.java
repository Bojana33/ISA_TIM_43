package isa2.demo.DTO.Mappers;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.CottageDTO;
import isa2.demo.DTO.ReservationDTO;
import isa2.demo.Model.AdditionalService;
import isa2.demo.Model.Cottage;
import isa2.demo.Model.Reservation;
import isa2.demo.Model.Room;
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
        Cottage cottage = modelMapper.modelMapper().map(cottageDTO, Cottage.class);
        cottage.setRooms(getRooms(cottage));
        cottage.setReservations(getReservations(cottage));
        //cottage.setCottageOwner();
        return cottage;
    }
    public CottageDTO mapCottageToDto(Cottage cottage){
        CottageDTO cottageDTO = modelMapper.modelMapper().map(cottage, CottageDTO.class);
        return cottageDTO;
    }
    private Collection<Room> getRooms(Cottage cottage) {
        Collection<Room> roomCollection = cottage.getRooms();
        if(roomCollection != null){
            for(Room room : roomCollection){
                room.setCottage(cottage);
            }
        }
        return roomCollection;
    }

    private Collection<Reservation> getReservations(Cottage cottage) {
        Collection<Reservation> reservationCollection = cottage.getReservations();

        if(reservationCollection != null){
            for(Reservation reservation : reservationCollection){
                reservation.setEntity(cottage);
                reservation.setCreationDate(LocalDateTime.now());
                Collection<AdditionalService> additionalServices = reservation.getAdditionalServices();
                if(additionalServices != null){
                    for(AdditionalService additionalService: additionalServices){
                        additionalService.setEntity(cottage);
                        additionalService.setReservation(reservation);
                    }
                }
             }
        }
        return reservationCollection;
    }

    public Collection<Reservation> mapCottageReservations(Collection<ReservationDTO> reservationDTOS){
        Collection<Reservation> reservations = new HashSet<>();
        for(ReservationDTO reservationDTO : reservationDTOS){
            reservations.add(modelMapper.modelMapper().map(reservationDTO, Reservation.class));
        }
        return reservations;
    }
}
