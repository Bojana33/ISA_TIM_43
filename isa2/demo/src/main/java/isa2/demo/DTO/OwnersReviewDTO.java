package isa2.demo.DTO;

import isa2.demo.Model.ClientsReviewStatus;
import isa2.demo.Model.ReviewStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OwnersReviewDTO {
    private Integer id;
    private String description;
    private Boolean appeared;
    private Boolean isReported;
    private ReviewStatus reviewStatus;
    private ReservationDTO reservationDTO;
}
