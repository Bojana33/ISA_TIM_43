import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MapService {

  private path!: string;
  constructor(
    private httpClient: HttpClient
  ) { }

  public getCordinates(address: string): Observable<JSON> {
    this.path = 'https://geocode-maps.yandex.ru/1.x/?apikey=cb834c63-c138-4b32-a96f-8e5b8427de81&geocode=' +
       address +
      '&format=json';;
    return this.httpClient.get<JSON>(this.path);
  }
}
