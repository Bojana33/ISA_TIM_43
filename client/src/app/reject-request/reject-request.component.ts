import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
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
    private formBuilder: FormBuilder,
    private http: HttpClient,
    private snackbar: MatSnackBar,
    private config: ConfigService
    ) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      rejectionReason: ''
    });
  }

  submit(){
    console.log();
    this.http.post(this.config.registration_request_url + '/reject_request/' + this.router.snapshot.params.id,JSON.parse(JSON.stringify(this.form.value.rejectionReason))).subscribe(res =>{
      console.log(res);
    });
  }

  rejectedSnackBar(){
    this.snackbar.open('Request is rejected','cancel');
  }
}
