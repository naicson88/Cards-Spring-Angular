import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { KonamiDeck } from 'src/app/classes/KonamiDeck';
import { AdminDashboardService } from '../admin-dashboard-service';
import {formatDate } from '@angular/common';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {

  formDeck: FormGroup;

  constructor(private adminService: AdminDashboardService, private http: HttpClient, private toastr: ToastrService) {}

  ngOnInit() {
    this.createFormDeck(new KonamiDeck())
  }
  onSubmit(){  

    this.formDeck.value.lancamento = formatDate(this.formDeck.value.lancamento, 'dd-MM-yyyy HH:mm:ss', 'en')
    
    this.adminService.createNewKonamiDeck(this.formDeck.value).subscribe(result => {
      console.warn(result);
      this.toastr.success("Deck information sent to Queue");
      this.formDeck.reset();
    }, error =>{
      console.log(error.msg)
    })
    
  }

  createFormDeck(konamiDeck:KonamiDeck){
    this.formDeck = new FormGroup({
      nome: new FormControl(konamiDeck.nome),
      nomePortugues: new FormControl(konamiDeck.nomePortugues),
      setType: new FormControl(konamiDeck.setType),
      lancamento: new FormControl(konamiDeck.lancamento),
      imagem: new FormControl(konamiDeck.imagem)
    })
  }

}
