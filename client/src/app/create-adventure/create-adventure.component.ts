import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
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
  
  selectedFile!: File;
  imageName: any;
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
    defaultFishingEquipment: new FormControl('')
})

constructor(
  private router: ActivatedRoute,
  private adventureService: AdventureService,
  private httpClient:HttpClient
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
  }

  createAdventure(){
    // this.onUpload();
    // this.adventure.value.entityPhoto = this.imageName;
    const data: FormData = new FormData();
    this.addAdventure(this.adventure);
    this.adventureService.saveAdventure(JSON.parse(JSON.stringify(this.adventureObj))).subscribe(res=>{console.log(res)});
  }
  
  public onFileChanged(event:any) {
    //Select File
    this.selectedFile = event.target.files[0];
  }

  public uploadPhoto(){
    this.createAdventure();
    const data: FormData = new FormData();
    data.append('imageUrl', this.selectedFile,this.selectedFile.name);
    this.adventureService.saveImage(data,this.adventure.get('id')?.value).subscribe(res=>{console.log(res)});
  }

}
