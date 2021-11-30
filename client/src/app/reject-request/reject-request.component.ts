import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { UserType } from '../enum/user-type';
import { RegistrationRequestService } from '../service/registration-request.service';
import { ConfigService } from '../service/config.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-reject-request',
  templateUrl: './reject-request.component.html',
  styleUrls: ['./reject-request.component.css']
})
export class RejectRequestComponent implements OnInit {
  form!: FormGroup;
  
  
  constructor(
    private router: ActivatedRoute,
    private requestService: RegistrationRequestService,
    private formBuilder: FormBuilder,
    private http: HttpClient,
    private snackbar: MatSnackBar,
    private config: ConfigService
    ) { }

  ngOnInit(): void {
    // console.warn(this.router.snapshot.params.id);
    // this.requestService.getRequest(this.router.snapshot.params.id)
    // .subscribe((result:any)=>{
    //   this.form = new FormGroup({
    //   rejectionReason: new FormControl(result['rejectionReason'])
    // })})
    this.form = this.formBuilder.group({
      rejectionReason: ''
    });
  }

  submit(){
    // console.warn(this.form.value);
    // this.requestService.rejectRequest(this.router.snapshot.params.id,this.form.value).subscribe((res:any)=>console.warn(res));
    console.log();
    this.http.post(this.config.registration_request_url + '/reject_request/' + this.router.snapshot.params.id,this.form.value,this.form.getRawValue())
    .subscribe(res =>{
      console.log(res);
    });
  }

  rejectedSnackBar(){
    this.snackbar.open('Request is rejected','cancel');
  }
}
