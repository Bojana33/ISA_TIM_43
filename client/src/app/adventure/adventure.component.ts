import { ReservationService } from './../service/reservation.service';
import { ApiService } from './../service/api.service';
import { MatDialog } from '@angular/material/dialog';
import { AdventureService } from './../service/adventure.service';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ConfigService } from '../service/config.service';
import { UserService } from '../service/user.service';
import { ReservationDTO } from '../model/reservation-dto.model';
import { DatePipe } from '@angular/common';

export interface Album{
  rows:number;
  cols:number;
}

@Component({
  selector: 'app-adventure',
  templateUrl: './adventure.component.html',
  styleUrls: ['./adventure.component.css']
})
export class AdventureComponent implements OnInit {

  adventure:any;
  selectedFile!: File;
  address!: string;
  addressTxt! : string;
  showForm= 1;

  constructor(
    private router: ActivatedRoute,
    private userService: UserService,
    private adventureService:AdventureService,
    private reservationService: ReservationService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.getAdventure();
  }


  getAdventure(){
    return this.adventureService.getAdventure(this.router.snapshot.params.id)
    .subscribe(response =>{
      console.log(response);
      this.adventure = response;
      this.address = this.adventure.addressDTO.country + ' ' + this.adventure.addressDTO.city + ' '  + this.adventure.addressDTO.street + '&kind=house&results=' + this.adventure.addressDTO.houseNumber;
      this.addressTxt = this.adventure.addressDTO.country + ' ' + this.adventure.addressDTO.city + ' '  + this.adventure.addressDTO.street + ' '+ this.adventure.addressDTO.houseNumber;
    });
  }
  
  hasRole(role:string){
    return this.userService.loggedRole(role);
  }

  isOwner(adventureOwnerId:number){
    return this.userService.currentUser.id == adventureOwnerId;
  }

  hasSignedIn() {
    return !!this.userService.currentUser;
  }

  deleteAdventure(){
    return this.adventureService.deleteAdventure(this.adventure.id);
  }

  public onFileChanged(event:any) {
    //Select File
    this.selectedFile = event.target.files[0];
  }

  public uploadPhoto(){
    const data: FormData = new FormData();
    data.append('imageUrl', this.selectedFile,this.selectedFile.name);
    this.adventureService.saveImage(data,this.router.snapshot.params.id).subscribe(res=>{console.log(res)});
  }

  createNewReservation($event: ReservationDTO){
    const datepipe = new DatePipe('en-US');
    let formatedReservationStartDate = datepipe.transform($event.reservedPeriod.startDate, 'yyyy-MM-dd HH:mm:ss');
    let formatedReservationEndDate = datepipe.transform($event.reservedPeriod.endDate, 'yyyy-MM-dd HH:mm:ss');
    let formatedSaleStartDate = datepipe.transform($event.salePeriod.startDate, 'yyyy-MM-dd HH:mm:ss');
    let formatedSaleEndDate = datepipe.transform($event.salePeriod.endDate, 'yyyy-MM-dd HH:mm:ss');
    console.log($event.reservedPeriod.startDate);
    console.log(formatedReservationStartDate);
    // @ts-ignore
    $event.reservedPeriod.startDate = new Date(formatedReservationStartDate.toString());
    console.log($event.reservedPeriod.startDate);
    // @ts-ignore
    $event.reservedPeriod.endDate = new Date(formatedReservationEndDate.toString());
    // @ts-ignore
    $event.salePeriod.startDate = new Date(formatedSaleStartDate.toString());
    // @ts-ignore
    $event.salePeriod.endDate = new Date(formatedSaleEndDate.toString());
    this.reservationService.createNewReservationForEntity($event).subscribe(
        (res: any) => {
        console.log(res);
      });
  }


}
