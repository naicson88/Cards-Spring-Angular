import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/classes/User';
import { AuthService } from 'src/app/service/auth-service/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  username: any;
  user: any;
  constructor(private auth: AuthService) { }

  ngOnInit() {
    this.getUser();
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
}
