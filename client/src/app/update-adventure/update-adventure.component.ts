import { ActivatedRoute } from '@angular/router';
import { Address } from './../model/address';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { AdventureService } from '../service/adventure.service';
import { Adventure } from '../model/adventure';

@Component({
  selector: 'app-update-adventure',
  templateUrl: './update-adventure.component.html',
  styleUrls: ['./update-adventure.component.css']
})
export class UpdateAdventureComponent implements OnInit {

  adventureObj! : Adventure;
  
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
      entityPhoto: new FormControl(''),
      defaultFishingEquipment: new FormControl(''),
      addresId : new FormControl(0),
      id : new FormControl(0),
  })

  constructor(
    private router: ActivatedRoute,
    private adventureService: AdventureService
  ) { }

  ngOnInit(): void {
    console.log(this.router.snapshot.params.id);
    this.adventureService.getAdventure(this.router.snapshot.params.id)
    .subscribe((res:any)=>{console.log(res);
      this.adventure = new FormGroup({
        name: new FormControl(res['name']),
        city: new FormControl(res.address['city']),
        country: new FormControl(res.address['country']),
        street: new FormControl(res.address['street']),
        houseNumber: new FormControl(res.address['houseNumber']),
        description: new FormControl(res['description']),
        instructorBio: new FormControl(res['instructorBio']),
        //photos: string[],
        maxNumberOfGuests: new FormControl(res['maxNumberOfGuests']),
        houseRules: new FormControl(res['houseRules']),
        //public additionalServices: AdditionalService,
        pricePerDay: new FormControl(res['pricePerDay']),
        cancellationFee: new FormControl(res['cancellationFee']),
        entityPhoto: new FormControl(res['entityPhoto']),
        defaultFishingEquipment: new FormControl(res['defaultFishingEquipment']),
        addresId : new FormControl(res.address['id']),
        id : new FormControl(res['id'])
    });
    });
    
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
    this.adventureObj.entityPhoto = form.value.entityPhoto;
    this.adventureObj.defaultFishingEquipment = form.value.defaultFishingEquipment;
    this.adventureObj.id = form.value.id;
    this.adventureObj.addressDTO.id= form.value.id;
  }

  updateAdventure(){
    console.log(this.adventure.value);
    this.addAdventure(this.adventure);
    this.adventureService.updateAdventure(this.router.snapshot.params.id,JSON.parse(JSON.stringify(this.adventureObj)))
    .subscribe((res)=>{console.log(res)});
  }

}
