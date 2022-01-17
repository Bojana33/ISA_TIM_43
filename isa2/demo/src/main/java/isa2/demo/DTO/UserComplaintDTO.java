package isa2.demo.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserComplaintDTO {
    private Integer id;
    private String description;
    private String response;
    private Boolean processed;
    //Integer reservationId;
    private ReservationDTO reservationDTO;
}
