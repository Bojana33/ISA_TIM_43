import { ApiService } from './../service/api.service';
import { MatDialog } from '@angular/material/dialog';
import { AdventureService } from './../service/adventure.service';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ConfigService } from '../service/config.service';
import { UserService } from '../service/user.service';

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

  album: Album[] = [
    {rows: 2, cols:2},
    {rows: 1, cols:1},
    {rows: 2, cols:1},
    {rows: 1, cols:1},

    {rows: 1, cols:1},
    {rows: 2, cols:2},
    {rows: 1, cols:1},
    {rows: 2, cols:1},
    {rows: 2, cols:1},

    {rows: 1, cols:1},
    {rows: 1, cols:1},
    {rows: 2, cols:2},
    {rows: 1, cols:1},
    {rows: 1, cols:1},
  ];

  constructor(
    private httpClient: HttpClient,
    private config: ConfigService,
    private router: ActivatedRoute,
    private userService: UserService,
    private adventureService: AdventureService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.album;
    this.getAdventure();
  }


  getAdventure(){
    return this.adventureService.getAdventure(this.router.snapshot.params.id)
    .subscribe(response =>{
      console.log(response);
      this.adventure = response;
      this.address = this.adventure.address.country + ' ' + this.adventure.address.city + ' '  + this.adventure.address.street + '&kind=house&results=' + this.adventure.address.houseNumber;
      this.addressTxt = this.adventure.address.country + ' ' + this.adventure.address.city + ' '  + this.adventure.address.street + ' '+ this.adventure.address.houseNumber;
    });
  }

  hasRole(role:string){
    return this.userService.loggedRole(role);
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


}
