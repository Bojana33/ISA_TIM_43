import { Component, Input, OnInit } from '@angular/core';
import { MapService } from '../service/map.service';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {

  @Input() address!: string;
  private pa!: string;
  public xcord!: number;
  public ycord!: number;

  constructor( private mapService: MapService
  ) { }

  ngOnInit(): void {
    this.address = this.address.replace(/,/g, '+');
    this.address = this.address.replace(/ /g, '+');
    this.mapService.getCordinates(this.address).
    subscribe((json: JSON) => {
      this.pa = JSON.stringify(json);
      this.nesto();
    });
  }

  nesto(): void {
    const n = this.pa.indexOf(',"Point":{"pos":"') + 17;
    const path  = this.pa.substr(n, 19);
    const splitted = path.split(' ', 2);
    this.ycord = Number(splitted[0].replace(/"/g, ''));
    this.xcord = Number(splitted[1].replace(/"/g, ''));
  }

}
