import { ConfigService } from './config.service';
import { ApiService } from './api.service';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ClientsReviewService {

  constructor(
    private apiService: ApiService,
    private config: ConfigService
  ) { }

  update(body:any){
    return this.apiService.put(this.config.clients_review_url + '/update_review', body);
  }

  getAllReviews(){
    return this.apiService.get(this.config.clients_review_url + '/get_reviews');
  }
}
