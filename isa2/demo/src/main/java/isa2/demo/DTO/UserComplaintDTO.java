package isa2.demo.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserComplaintDTO {
    Integer id;
    String description;
    String response;
    Boolean processed;
    Integer reservationId;
}
