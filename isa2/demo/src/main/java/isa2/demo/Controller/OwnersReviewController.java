package isa2.demo.Controller;

import isa2.demo.DTO.Mappers.ClientsReviewMapper;
import isa2.demo.DTO.Mappers.OwnersReviewMapper;
import isa2.demo.DTO.OwnersReviewDTO;
import isa2.demo.Model.OwnersReview;
import isa2.demo.Service.OwnersReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/owners_review")
public class OwnersReviewController {
    
    public final OwnersReviewService ownersReviewService;
    public final OwnersReviewMapper ownersReviewMapper;
    
    public OwnersReviewController(OwnersReviewService ownersReviewService, OwnersReviewMapper ownersReviewMapper){
        this.ownersReviewService = ownersReviewService;
        this.ownersReviewMapper = ownersReviewMapper;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('COTTAGEOWNER','BOATOWNER','INSTRUCTOR')")
    @PostMapping(value = "/save_review")
    public ResponseEntity<? extends Object> createClientReview(@RequestBody OwnersReviewDTO ownersReviewDTO){
        OwnersReview ownersReview = this.ownersReviewMapper.mapDtoToOwnersReview(ownersReviewDTO);
        try{
            OwnersReview review = this.ownersReviewService.save(ownersReview);
            return new ResponseEntity<OwnersReviewDTO>(this.ownersReviewMapper.mapOwnersReviewToDto(review), HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(new Exception("Forbidden"), HttpStatus.FORBIDDEN);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'COTTAGEOWNER', 'BOATOWNER', 'INSTRUCTOR')")
    @PutMapping("/update_review")
    public ResponseEntity<OwnersReviewDTO> updateUserComplaint(@RequestBody OwnersReviewDTO ownersReviewDTO){
        try {
            OwnersReview ownersReview = this.ownersReviewMapper.mapDtoToOwnersReview(ownersReviewDTO);
            ownersReview = this.ownersReviewService.update(ownersReview);
            ownersReviewDTO = this.ownersReviewMapper.mapOwnersReviewToDto(ownersReview);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(ownersReviewDTO,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/get_reviews")
    public ResponseEntity<List<OwnersReviewDTO>> getReviews(){
        List<OwnersReview> ownersReviews = this.ownersReviewService.findAllByStatusOnHoldAndReportedTrue();
        List<OwnersReviewDTO> ownersReviewDTOS = new ArrayList<>();
        for(OwnersReview ownersReview: ownersReviews){
            ownersReviewDTOS.add(this.ownersReviewMapper.mapOwnersReviewToDto(ownersReview));
        }
        return new ResponseEntity<>(ownersReviewDTOS,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/send_response")
    public void sendResponse(@RequestBody OwnersReviewDTO ownersReviewDTO){
        this.ownersReviewService.sendResponse(this.ownersReviewMapper.mapDtoToOwnersReview(ownersReviewDTO));
    }
}
