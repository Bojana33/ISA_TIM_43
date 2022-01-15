import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { Address } from './../model/address';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { AdventureService } from '../service/adventure.service';
import { Adventure } from '../model/adventure';

@Component({
  selector: 'app-update-adventure',
  templateUrl: './update-adventure.component.html',
  styleUrls: ['./update-adventure.component.css']
})
export class UpdateAdventureComponent implements OnInit {

  adventureObj : Adventure = new Adventure();

  // @ts-ignore
  adventure: FormGroup;
  success!: boolean;
  
  // new FormGroup({
  //     name: new FormControl(''),
  //     city: new FormControl(''),
  //     country: new FormControl(''),
  //     street: new FormControl(''),
  //     houseNumber: new FormControl(''),
  //     description: new FormControl(''),
  //     instructorBio: new FormControl(''),
  //     //photos: string[],
  //     maxNumberOfGuests: new FormControl(0),
  //     houseRules: new FormControl(''),
  //     //public additionalServices: AdditionalService,
  //     pricePerDay: new FormControl(0.0),
  //     cancellationFee: new FormControl(0),
  //     entityPhoto: new FormControl(''),
  //     defaultFishingEquipment: new FormControl(''),
  //     addressId : new FormControl(0),
  //     id : new FormControl(0),
  //     adventureOwnerId: new FormControl(0)
  // })

  constructor(
    private router: ActivatedRoute,
    private route: Router,
    private adventureService: AdventureService,
    private formBuilder: FormBuilder,
    private snackbar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.loadData();
  }

  loadData(){
    this.adventureService.getAdventure(this.router.snapshot.params.id)
    .subscribe((res:any)=>{console.log(res);
      this.adventureObj = res;
      this.adventure = this.formBuilder.group({
        name: new FormControl(this.adventureObj.name,[
          Validators.required,
          Validators.minLength(5)]),
        pricePerDay: new FormControl(this.adventureObj.pricePerDay,[
          Validators.required]),
        city: new FormControl(this.adventureObj.addressDTO.city,[
          Validators.required]),
        country: new FormControl(this.adventureObj.addressDTO.country,[
          Validators.required]),
        street: new FormControl(this.adventureObj.addressDTO.street,[
          Validators.required]),
        houseNumber: new FormControl(this.adventureObj.addressDTO.houseNumber,[
          Validators.required]),
        description: new FormControl(this.adventureObj.description,[
          Validators.required]),
        instructorBio: new FormControl(this.adventureObj.instructorBio,[
          Validators.required]),
        maxNumberOfGuests: new FormControl(this.adventureObj.maxNumberOfGuests,[
          Validators.required]),
        houseRules: new FormControl(this.adventureObj.houseRules,[
          Validators.required]),
        cancellationFee: new FormControl(this.adventureObj.cancellationFee,[
          Validators.required]),
        defaultFishingEquipment: new FormControl(this.adventureObj.defaultFishingEquipment,[
          Validators.required])
      })
    });
  }

  addAdventure(form:any){
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
    this.adventureObj.defaultFishingEquipment = form.value.defaultFishingEquipment;
  }

  updateAdventure(){
    this.addAdventure(this.adventure);
    this.adventureService.updateAdventure(this.router.snapshot.params.id,JSON.parse(JSON.stringify(this.adventureObj)))
    .subscribe((res)=>{console.log(res); this.loadData(); this.success=true; this.openSnackBar(); this.route.navigate(['/adventure/',this.adventureObj.id])}
      , error => {
        this.success = false;
        this.openSnackBar();
      });
  }

  openSnackBar(){
    let message: string;
    if( this.success == true){
      message = 'Update successful';
    } else{
      message = 'Update failed';
    }
    this.snackbar.open(message,'cancel');
  }

}
