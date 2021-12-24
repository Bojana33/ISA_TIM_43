import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormControl } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute } from '@angular/router';
import { ConfigService } from '../service/config.service';
import { UserDeleteRequestService } from '../service/user-delete-request.service';

@Component({
  selector: 'app-delete-request-response',
  templateUrl: './delete-request-response.component.html',
  styleUrls: ['./delete-request-response.component.css']
})
export class DeleteRequestResponseComponent implements OnInit {

  form!: FormGroup;

  constructor(
    private router: ActivatedRoute,
    private snackbar: MatSnackBar,
    private deleteRequestService: UserDeleteRequestService
  ) { }

  req = new FormGroup({
    id: new FormControl(0),
    description: new FormControl(''),
    response: new FormControl(''),
    userId: new FormControl(0)
  })

  ngOnInit(): void {
    this.deleteRequestService.getRequest(this.router.snapshot.params.id)
    .subscribe(res => 
      {
        this.req =new FormGroup({
          id: new FormControl(res['id']),
          description: new FormControl(res['description']),
          response: new FormControl(res['response']),
          userId: new FormControl(res['userId'])
        });
         console.log(res);
      });
  }

  sendResponse(){
    if (this.router.snapshot.params.isApproved === "true"){
      return this.deleteRequestService.approveRequest(this.req.value).subscribe(res=>{console.log(res)});
    } else {
      return this.deleteRequestService.rejectRequest(JSON.stringify(this.req.value)).subscribe(res=>{console.log(res)});
    }
  }

  openSnackBar(){
    if (this.router.snapshot.params.isApproved === "true"){
      this.snackbar.open('Request is approved','cancel');
    } else {
      this.snackbar.open('Request is rejected','cancel');
    }
  }

}
