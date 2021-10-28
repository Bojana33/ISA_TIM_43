package isa2.demo.Model;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Entity;

@Data
@Entity
public class ConfigSingleton {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "config_singleton_id", unique = true, nullable = false)
    private Integer id;

    @Column
    private java.lang.Double feePercentage;
    @Column
    private java.lang.Double clientReservationPoints;
    @Column
    private java.lang.Double successfulOwnerPoints;
    @Column
    private java.lang.Double regularStartPoints;
    @Column
    private java.lang.Double silverStartPoints;
    @Column
    private java.lang.Double goldStartPoints;
    @Column
    private java.lang.Double discountRegular;
    @Column
    private java.lang.Double discountGold;
    @Column
    private java.lang.Double discountSilver;
    @Column
    private java.lang.Double incomeRegular;
    @Column
    private java.lang.Double incomeGold;
    @Column
    private java.lang.Double incomeSilver;

}
