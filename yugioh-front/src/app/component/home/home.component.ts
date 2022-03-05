import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { Role } from 'src/app/classes/Role';
import { User } from 'src/app/classes/User';
import { AuthService } from 'src/app/service/auth-service/auth.service';
import { HomeService } from './home.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  username: any;
  user: any;

  constructor( private service: HomeService, private router :Router) { }

  ngOnInit() {
   // this.getUser();
    this.loadHomeInfo();
  }

  

    infoHome: any[] = [];
    img:string

    loadHomeInfo(){
      this.service.loadHomeInfo().subscribe(info => {
        this.infoHome = info;
        console.log(this.infoHome);
       this.img = this.infoHome['lastSets'].img
      

      }), error =>{
        let errorCode = error.status;
        this.router.navigate(["/error-page", errorCode]);
      }
    }

    storeDeckId(id:any){
      //  const id = event.target.name;
        localStorage.setItem("idDeckDetails", id);
      
      }

}
