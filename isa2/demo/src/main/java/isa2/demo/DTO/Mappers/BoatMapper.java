package isa2.demo.DTO.Mappers;

import isa2.demo.Config.ModelMapperConfig;
import isa2.demo.DTO.AddressDTO;
import isa2.demo.DTO.AdventureDTO;
import isa2.demo.DTO.BoatDTO;
import isa2.demo.DTO.ReservationDTO;
import isa2.demo.Model.*;
import isa2.demo.Service.ClientService;
import isa2.demo.Service.ServiceImpl.OwnerServiceImpl;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;

@Component
public class BoatMapper {

    public final ModelMapperConfig modelMapper;
    public final OwnerServiceImpl ownerService;
    public final ClientService clientService;

    public BoatMapper(ModelMapperConfig modelMapper, OwnerServiceImpl ownerService, ClientService clientService) {
        this.modelMapper = modelMapper;
        this.ownerService = ownerService;
        this.clientService = clientService;
    }

    public Boat mapDtoToBoat(BoatDTO boatDTO){
        Boat boat = modelMapper.modelMapper().map(boatDTO, Boat.class);
        Collection<Reservation> reservationCollection = boat.getReservations();
        if(!reservationCollection.isEmpty() || reservationCollection != null) {
            Iterator<Reservation> iterator1 = reservationCollection.iterator();
            Iterator<ReservationDTO> iterator2 = boatDTO.getReservations().iterator();
            while (iterator1.hasNext() && iterator2.hasNext()) {
                Reservation temp_reservation = iterator1.next();
                ReservationDTO temp_reservationDTO = iterator2.next();

                if (temp_reservationDTO.getClientId() != null)
                    temp_reservation.setClient(clientService.findById(temp_reservationDTO.getClientId()));

                temp_reservation.setEntity(boat);
                //zasto je ovo u mapperu?
                if(temp_reservation.getCreationDate() == null)
                    temp_reservation.setCreationDate(LocalDateTime.now());
                Collection<AdditionalService> additionalServices = temp_reservation.getAdditionalServices();
                if (additionalServices != null) {
                    for (AdditionalService additionalService : additionalServices) {
                        additionalService.setEntity(boat);
                        additionalService.setReservation(temp_reservation);
                    }
                }
            }
        }
        boat.setReservations(reservationCollection);
        return boat;
    }
    public BoatDTO mapBoatToDTO(Boat boat){
        BoatDTO boatDTO = modelMapper.modelMapper().map(boat, BoatDTO.class);
        AddressDTO addressDTO = modelMapper.modelMapper().map(boat.getAddress(),AddressDTO.class);
        boatDTO.setAddress(addressDTO);
        return boatDTO;
    }
}
