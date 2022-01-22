package isa2.demo.DTO.Mappers;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.CottageDTO;
import isa2.demo.DTO.ReservationDTO;
import isa2.demo.Model.AdditionalService;
import isa2.demo.Model.Cottage;
import isa2.demo.Model.Reservation;
import isa2.demo.Model.Room;
import isa2.demo.Service.ClientService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

@Component
public class CottageMapper {

    public final ModelMapperConfig modelMapper;
    public final ClientService clientService;

    public CottageMapper(ModelMapperConfig modelMapper, ClientService clientService) {
        this.modelMapper = modelMapper;
        this.clientService = clientService;
    }

    public Cottage mapDtoToCottage(CottageDTO cottageDTO){
        Cottage cottage = modelMapper.modelMapper().map(cottageDTO, Cottage.class);
        Collection<Reservation> reservationCollection = cottage.getReservations();
        if(!reservationCollection.isEmpty() || reservationCollection != null) {
            Iterator<Reservation> iterator1 = reservationCollection.iterator();
            Iterator<ReservationDTO> iterator2 = cottageDTO.getReservations().iterator();
            while (iterator1.hasNext() && iterator2.hasNext()) {
                Reservation temp_reservation = iterator1.next();
                ReservationDTO temp_reservationDTO = iterator2.next();

                //if (temp_reservationDTO.getClientId() != null)
                  //  temp_reservation.setClient(clientService.findById(Integer.parseInt(temp_reservationDTO.getClientId()));

                temp_reservation.setEntity(cottage);
                if(temp_reservationDTO.getClientId() != null)
                    temp_reservation.setClient(clientService.findById(temp_reservationDTO.getClientId()));
                //zasto je ovo u mapperu?
                if(temp_reservation.getCreationDate() == null)
                    temp_reservation.setCreationDate(LocalDateTime.now());
                Collection<AdditionalService> additionalServices = temp_reservation.getAdditionalServices();
                if (additionalServices != null) {
                    for (AdditionalService additionalService : additionalServices) {
                        additionalService.setEntity(cottage);
                        additionalService.setReservation(temp_reservation);
                    }
                }
            }
        }
        cottage.setRooms(getRooms(cottage));
        cottage.setReservations(reservationCollection);
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

//    private Collection<Reservation> getReservations(Cottage cottage) {
//        Collection<Reservation> reservationCollection = cottage.getReservations();
//
//        if(reservationCollection != null){
//            for(Reservation reservation : reservationCollection){
//                reservation.setEntity(cottage);
//                reservation.setCreationDate(LocalDateTime.now());
//                Collection<AdditionalService> additionalServices = reservation.getAdditionalServices();
//                if(additionalServices != null){
//                    for(AdditionalService additionalService: additionalServices){
//                        additionalService.setEntity(cottage);
//                        additionalService.setReservation(reservation);
//                    }
//                }
//             }
//        }
//        return reservationCollection;
//    }

    public Collection<Reservation> mapCottageReservations(Collection<ReservationDTO> reservationDTOS){
        Collection<Reservation> reservations = new HashSet<>();
        for(ReservationDTO reservationDTO : reservationDTOS){
            reservations.add(modelMapper.modelMapper().map(reservationDTO, Reservation.class));
        }
        return reservations;
    }
}
