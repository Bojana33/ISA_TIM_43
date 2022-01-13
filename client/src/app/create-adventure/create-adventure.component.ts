import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from './../service/user.service';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { __param } from 'tslib';
import { Address } from '../model/address';
import { Adventure } from '../model/adventure';
import { AdventureService } from '../service/adventure.service';

@Component({
  selector: 'app-create-adventure',
  templateUrl: './create-adventure.component.html',
  styleUrls: ['./create-adventure.component.css']
})
export class CreateAdventureComponent implements OnInit {
  
  adventureObj! : Adventure;
  success!: boolean;

  adventure = new FormGroup({
    name: new FormControl(''),
    city: new FormControl(''),
    country: new FormControl(''),
    street: new FormControl(''),
    houseNumber: new FormControl(''),
    description: new FormControl(''),
    instructorBio: new FormControl(''),
    //photos: string[],
    maxNumberOfGuests: new FormControl(0),
    houseRules: new FormControl(''),
    //public additionalServices: AdditionalService,
    pricePerDay: new FormControl(0.0),
    cancellationFee: new FormControl(0),
    defaultFishingEquipment: new FormControl('')
})

constructor(
  private router: ActivatedRoute,
  private route: Router,
  private adventureService: AdventureService,
  private userService: UserService,
  private snackbar: MatSnackBar
) { }

  ngOnInit(): void {
  }

  addAdventure(form:any){
    this.adventureObj = new Adventure();
    this.adventureObj.name = form.value.name;
    this.adventureObj.addressDTO.city = form.value.city;
    this.adventureObj.addressDTO.country = form.value.country;
    this.adventureObj.addressDTO.street = form.value.street;
    this.adventureObj.addressDTO.houseNumber = form.value.houseNumber;
    this.adventureObj.description = form.value.description;
    this.adventureObj.instructorBio = form.value.instructorBio;
    this.adventureObj.maxNumberOfGuests = form.value.maxNumberOfGuests;
    this.adventureObj.houseRules = form.value.houseRules;
    this.adventureObj.pricePerDay = form.value.pricePerDay;
    this.adventureObj.cancellationFee = form.value.cancellationFee;
    this.adventureObj.entityPhoto = "./../../assets/images/capsule_616x353.jpg";
    this.adventureObj.defaultFishingEquipment = form.value.defaultFishingEquipment;
    this.adventureObj.adventureOwnerId = this.userService.currentUser.id;
  }

  createAdventure(){
    this.addAdventure(this.adventure);
    this.adventureService.saveAdventure(JSON.parse(JSON.stringify(this.adventureObj))).subscribe(res=>{console.log(res); this.success= true; this.route.navigate(['/adventures']); this.openSnackBar();},
      error => {
        this.success = false;
        this.openSnackBar();
      });
  }

  openSnackBar(){
    let message: string;
    if( this.success == true){
      message = 'Adventure created!';
    } else{
      message = 'Adventure creation failed';
    }
    this.snackbar.open(message,'cancel');
  }

}
