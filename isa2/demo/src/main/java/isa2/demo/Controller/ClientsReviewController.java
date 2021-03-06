package isa2.demo.Controller;

import isa2.demo.DTO.ClientsReviewDTO;
import isa2.demo.DTO.Mappers.ClientsReviewMapper;
import isa2.demo.DTO.UserComplaintDTO;
import isa2.demo.Model.ClientsReview;
import isa2.demo.Service.ClientsReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/clients_review")
public class ClientsReviewController {

    public final ClientsReviewService clientsReviewService;

    public final ClientsReviewMapper clientsReviewMapper;


    public ClientsReviewController(ClientsReviewService clientsReviewService, ClientsReviewMapper clientsReviewMapper){
        this.clientsReviewService = clientsReviewService;
        this.clientsReviewMapper = clientsReviewMapper;
    }

    @ResponseStatus(HttpStatus.CREATED)

    @PostMapping(value = "/save_review")
    public ResponseEntity<? extends Object> createClientReview(@RequestBody ClientsReviewDTO clientsReviewDTO, Principal username){
        ClientsReview clientsReview = this.clientsReviewMapper.mapDtoToClientsReview(clientsReviewDTO);
        try{
            ClientsReview review = this.clientsReviewService.save(clientsReview, username.getName());
            return new ResponseEntity<ClientsReviewDTO>(this.clientsReviewMapper.mapClientsReviewToDto(review), HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(new Exception("Forbidden"), HttpStatus.FORBIDDEN);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    @PutMapping("/update_review")
    public ResponseEntity<ClientsReviewDTO> updateClientsReview(@RequestBody ClientsReviewDTO clientsReviewDTO){
        try {
            ClientsReview clientsReview = this.clientsReviewMapper.mapDtoToClientsReview(clientsReviewDTO);
            clientsReview = this.clientsReviewService.update(clientsReview);
            clientsReviewDTO = this.clientsReviewMapper.mapClientsReviewToDto(clientsReview);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(clientsReviewDTO,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get_reviews")
    public ResponseEntity<List<ClientsReviewDTO>> getUnprocessedReviews(){
        List<ClientsReview> clientsReviews = this.clientsReviewService.findAllUnprocessedReviews();
        List<ClientsReviewDTO> clientsReviewDTOS = new ArrayList<>();
        for(ClientsReview clientsReview: clientsReviews){
            clientsReviewDTOS.add(this.clientsReviewMapper.mapClientsReviewToDto(clientsReview));
        }
        return new ResponseEntity<>(clientsReviewDTOS,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/send_response")
    public void sendResponse(@RequestBody ClientsReviewDTO clientsReviewDTO){
        this.clientsReviewService.sendResponse(this.clientsReviewMapper.mapDtoToClientsReview(clientsReviewDTO));
    }
}
