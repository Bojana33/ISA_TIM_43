import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {ConfigService} from '../../service/config.service';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../service/user.service';
import {AddressDTO} from '../../model/address-dto.model';
import {UserDTO} from '../../model/user-dto';
import {ProfileComponent} from "../profile.component";



@Component({
  selector: 'app-profile-update',
  templateUrl: './profile-update.component.html',
  styleUrls: ['./profile-update.component.css']
})
export class ProfileUpdateComponent implements OnInit {

  firstName: any;
  surname: any;
  phoneNumber: any;
  city: any;
  street: any;
  houseNumber: any;
  country: any;
  user: UserDTO = new UserDTO();
  userProfile = new FormGroup({
    firstName: new FormControl(),
    surname: new FormControl(''),
    phoneNumber: new FormControl(''),
    city: new FormControl(''),
    street: new FormControl(''),
    country: new FormControl(''),
    houseNumber: new FormControl('')
  });


  constructor(
    private httpClient: HttpClient,
    private config: ConfigService,
    private router: ActivatedRoute,
    private router2: Router,
    private userService: UserService) {
  }

  ngOnInit(): void {
    this.userService.getMyInfo().subscribe(res => this.userProfile = new FormGroup({
      firstName: new FormControl(res['firstName']),
      surname: new FormControl(res['surname']),
      phoneNumber: new FormControl(res['phoneNumber']),
      city: new FormControl(res.address['city']),
      country: new FormControl(res.address['country']),
      street: new FormControl(res.address['street']),
      houseNumber: new FormControl(res.address['houseNumber'])
    }));
  }

  onSubmit(values: any){
    console.log(values.country);
    this.user.firstName = values.firstName;
    this.user.surname = values.surname;
    this.user.phoneNumber = values.phoneNumber;
    this.user.address.street = values.street;
    this.user.address.country = values.country;
    this.user.address.city = values.city;
    this.user.address.houseNumber = values.houseNumber;
    this.userService.update(this.user);
    this.router2.navigateByUrl('/', {skipLocationChange: true}).then(() => {
      this.router2.navigate(['/profile/',this.router.snapshot.params.id]).then(() => {
        //window.location.reload();
      });
    });
  }
}
