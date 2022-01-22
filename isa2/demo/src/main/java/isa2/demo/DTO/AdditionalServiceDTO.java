package isa2.demo.DTO;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class AdditionalServiceDTO {
    private Integer id;
    @Length(min = 5, max = 63)
    private java.lang.String name;
    @Positive
    private java.lang.Double price;
}
