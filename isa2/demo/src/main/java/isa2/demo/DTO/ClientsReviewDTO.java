package isa2.demo.DTO;

import isa2.demo.Model.ClientsReviewStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientsReviewDTO {
    private Integer id;
    private String description;
    private Double grade;
    private ClientsReviewStatus status;
    private ReservationDTO reservationDTO;
}
