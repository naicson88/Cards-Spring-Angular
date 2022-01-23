import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
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
  constructor(private auth: AuthService, private service: HomeService, private router :Router) { }

  ngOnInit() {
    this.getUser();
    this.loadHomeInfo();
  }

    getUser(): Observable<User>{
      this.auth.getCurrentUser$().subscribe(data=>{
        this.username = data
      })

      this.auth.consultarUsuarioLogado(this.username.sub).subscribe(data =>{
        this.user = data;
        
      });

      return this.user;
     
    }
    infoHome: any[] = [];
    img:string

    loadHomeInfo(){
      this.service.loadHomeInfo().subscribe(info => {
        this.infoHome = info;
       this.img = this.infoHome['lastSets'].img
       console.log(this.img)
        console.log(this.infoHome)

      }), error =>{
        let errorCode = error.status;
        this.router.navigate(["/error-page", errorCode]);
      }
    }

    storeDeckId(id:any){
      //  const id = event.target.name;
      console.log(id);
        localStorage.setItem("idDeckDetails", id);
      
      }
}
