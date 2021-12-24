package isa2.demo.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class AdditionalServiceDTO {
    private Integer id;
    private java.lang.String name;
    private java.lang.Double price;
}
