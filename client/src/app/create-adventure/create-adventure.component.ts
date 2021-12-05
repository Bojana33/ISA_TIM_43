import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { __param } from 'tslib';
import { AdventureService } from '../service/adventure.service';

@Component({
  selector: 'app-create-adventure',
  templateUrl: './create-adventure.component.html',
  styleUrls: ['./create-adventure.component.css']
})
export class CreateAdventureComponent implements OnInit {
  
  selectedFile!: File;
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;
  message!: string;
  imageName: any;

  adventure = new FormGroup({
    name: new FormControl(''),
    //address: new FormControl(Address),
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

  createAdventure(){
    // this.onUpload();
    // this.adventure.value.entityPhoto = this.imageName;
    const data: FormData = new FormData();
    this.adventureService.saveAdventure(this.adventure.value).subscribe(res=>{console.log(res)});
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
