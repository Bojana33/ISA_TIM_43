import { ConfigService } from './config.service';
import { ApiService } from './api.service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class OwnersReviewService {

  constructor(
    private apiService: ApiService,
    private config: ConfigService
  ) { }

  update(body:any){
    return this.apiService.put(this.config.owners_review_url + '/update_review', body);
  }

  getAllReviews(){
    return this.apiService.get(this.config.owners_review_url + '/get_reviews');
  }

  createReview(body: any) {
    return this.apiService.post(this.config.owners_review_url + '/save_review', body);
  }

  sendResponse(body:any){
    return this.apiService.post(this.config.owners_review_url + '/send_response', body);
  }
}
