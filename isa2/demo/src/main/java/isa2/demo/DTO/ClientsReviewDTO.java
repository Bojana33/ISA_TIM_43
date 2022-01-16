package isa2.demo.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientsReviewDTO {
    private Integer id;
    private String description;
    private Double grade;
    private ReservationDTO reservationDTO;
}
