import { MatSnackBar } from '@angular/material/snack-bar';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ApiService } from './../service/api.service';
import { Component, OnInit } from '@angular/core';
import { ConfigSingleton } from '../model/config-singleton';

@Component({
  selector: 'app-config-singleton',
  templateUrl: './config-singleton.component.html',
  styleUrls: ['./config-singleton.component.css']
})
export class ConfigSingletonComponent implements OnInit {

  configForm!: FormGroup;
  config: ConfigSingleton = new ConfigSingleton();
  success!: boolean;

  constructor(
    private apiService: ApiService,
    private formBuilder: FormBuilder,
    private snackbar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.loadData();
  }

  loadData(){
    this.apiService.get('http://localhost:8090/config/get_config')
    .subscribe((res:any)=>{console.log(res);
      this.config = res;
      this.configForm = this.formBuilder.group({
        feePercentage: new FormControl(this.config.feePercentage,[
          Validators.required]),
        clientReservationPoints: new FormControl(this.config.clientReservationPoints,[
          Validators.required]),
        successfulOwnerPoints: new FormControl(this.config.successfulOwnerPoints,[
          Validators.required]),
        regularStartPoints: new FormControl(this.config.regularStartPoints,[
          Validators.required]),
        silverStartPoints: new FormControl(this.config.silverStartPoints,[
          Validators.required]),
        goldStartPoints: new FormControl(this.config.goldStartPoints,[
          Validators.required]),
        discountRegular: new FormControl(this.config.discountRegular,[
          Validators.required]),
        discountGold: new FormControl(this.config.discountGold,[
          Validators.required]),
        discountSilver: new FormControl(this.config.discountSilver,[
          Validators.required]),
        incomeRegular: new FormControl(this.config.incomeRegular,[
          Validators.required]),
        incomeGold: new FormControl(this.config.incomeGold,[
          Validators.required]),
        incomeSilver: new FormControl(this.config.incomeSilver,[
          Validators.required])
      })
    });
  }

  addConfig(form:any){
    this.config.feePercentage = form.value.feePercentage;
    this.config.clientReservationPoints = form.value.clientReservationPoints;
    this.config.successfulOwnerPoints = form.value.successfulOwnerPoints;
    this.config.regularStartPoints = form.value.regularStartPoints;
    this.config.silverStartPoints = form.value.silverStartPoints;
    this.config.goldStartPoints = form.value.goldStartPoints;
    this.config.discountRegular = form.value.discountRegular;
    this.config.discountGold = form.value.discountGold;
    this.config.discountSilver = form.value.discountSilver;
    this.config.incomeRegular = form.value.incomeRegular;
    this.config.incomeGold = form.value.incomeGold;
    this.config.incomeSilver = form.value.incomeSilver;
  }

  updateConfig(){
    this.addConfig(this.configForm);
    this.apiService.put('http://localhost:8090/config/update',JSON.parse(JSON.stringify(this.config)))
    .subscribe((res)=>{console.log(res); this.loadData(); this.success=true; this.openSnackBar();}
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
