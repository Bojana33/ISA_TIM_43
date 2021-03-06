package isa2.demo.Model;

import lombok.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "client_reviews")
public class ClientsReview implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_review_id", unique = true, nullable = false)
    private Integer id;

    @Column
    private java.lang.String description;

    @Column
    private java.lang.Double grade;

    @Column
    private ClientsReviewStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", referencedColumnName = "id")
    private Reservation reservation;

}
