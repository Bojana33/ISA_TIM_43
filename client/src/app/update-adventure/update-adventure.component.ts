import { ActivatedRoute } from '@angular/router';
import { Address } from './../model/address';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { AdventureService } from '../service/adventure.service';

@Component({
  selector: 'app-update-adventure',
  templateUrl: './update-adventure.component.html',
  styleUrls: ['./update-adventure.component.css']
})
export class UpdateAdventureComponent implements OnInit {

  adventure = new FormGroup({
      name: new FormControl(''),
      address: new FormControl(Address),
      description: new FormControl(''),
      instructorBio: new FormControl(''),
      //photos: string[],
      maxNumberOfGuests: new FormControl(0),
      houseRules: new FormControl(''),
      //public additionalServices: AdditionalService,
      pricePerDay: new FormControl(0.0),
      cancellationFee: new FormControl(0),
      entityPhoto: new FormControl(''),
      defaultFishingEquipment: new FormControl('')
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
        address: new FormControl(res['address']),
        description: new FormControl(res['description']),
        instructorBio: new FormControl(res['instructorBio']),
        //photos: string[],
        maxNumberOfGuests: new FormControl(res['maxNumberOfGuests']),
        houseRules: new FormControl(res['houseRules']),
        //public additionalServices: AdditionalService,
        pricePerDay: new FormControl(res['pricePerDay']),
        cancellationFee: new FormControl(res['cancellationFee']),
        entityPhoto: new FormControl(res['entityPhoto']),
        defaultFishingEquipment: new FormControl(res['defaultFishingEquipment'])
    })
    });
    
  }

  updateAdventure(){
    console.log(this.adventure.value);
    this.adventureService.updateAdventure(this.router.snapshot.params.id,this.adventure.value)
    .subscribe((res)=>{console.log});
  }

}
