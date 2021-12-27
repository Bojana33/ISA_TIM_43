package isa2.demo.DTO;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDeleteRequestDTO {
    private Integer id;
    private String description;
    private String response;
    private Integer userId;

}
