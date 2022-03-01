import { animate, state, style, transition, trigger } from '@angular/animations';
import { Component, OnChanges, OnInit } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth-service/auth.service';

@Component({
  selector: 'app-side-menu',
  templateUrl: './side-menu.component.html',
  styleUrls: ['./side-menu.component.css'],
  animations: [
    trigger('openClose', [
      // ...
      state('open', style({
        marginLeft: "260px"
      })),
      state('closed', style({
        marginLeft: "20px"
      })),
      transition('open <=> closed', [
        animate('0.3s')
      ]),  
    ]),

    trigger('hideShowMenu', [
      // ...
      state('open', style({
        marginLeft: "0"
      })),
      state('closed', style({
        marginLeft: "-260px"
      })),
      transition('open <=> closed', [
        animate('0.3s')
      ]),  
    ]),


    trigger('hideShowMenuOptions', [
      // ...
      state('open', style({
       display: 'block'
      })),
      state('closed', style({
        display: 'none'
      })),

    ]),

  ],

})


export class SideMenuComponent implements OnInit {

  isShowDeck: boolean = false;
  isShowCard: boolean = false;
  isIconeExpand: boolean = false;
  isIndex: boolean = false;
  isLogin: boolean = false;
  isRegister: boolean = false;
  isAdminOrModerator: boolean = false;

  constructor(private router: Router, private authService: AuthService, ) {
 
   }

  ngOnInit() {
   
    this.checkRouter();
    this.validUser();
  }

  checkRouter(){
    this.router.events.subscribe((event:any) => {
      
      if(event instanceof NavigationEnd) {

        if(event.url === '/index'){
          this.isIndex = true;
        } 
        else if(event.url === '/login'){
          this.isLogin = true;
        }
        else if(event.url === '/register'){
          this.isRegister = true
        } 
        else if(event.url === '/'){
          this.isIndex = true

        } else {
          this.isIndex = false;
          this.isLogin = false;
          this.isRegister = false;
        }
        
      }
    }) 
  }

  mostrarUlDecks(){

    if(this.isShowDeck == false){
     this.isShowDeck = true;
    } else {
      this.isShowDeck = false;
    }
  }

  mostrarUlCards(){
    if(this.isShowCard == false){
      this.isShowCard = true;
     } else {
       this.isShowCard = false;
     }
  }

  marginIconeLateral(trans: string){

    if(this.isIconeExpand == false){
      this.isIconeExpand = true;
     } else {
       this.isIconeExpand = false;
     }
  }

  logout() {
    this.authService.logout();
    this.router.navigate(['/index'])
  }

  validUser(){
    
    this.authService.getUser().subscribe(userReturned => { 
          
      this.checkIfIsAdmin(userReturned.roles[0].roleName)
    }, error => {
      console.log("Error when try to consult user" + error.erro);
      this.router.navigate(['/error-page', 500])
    })
  }
  
checkIfIsAdmin(userRole:string) {   
    //  let user:string = localStorage.getItem('currentUser');
      
        if(userRole == undefined || userRole == null)
            this.router.navigate(['/login'])

        if(userRole == "ROLE_ADMIN"  || userRole == "ROLE_MODERATOR")
          this.isAdminOrModerator = true;

        else if(userRole == "ROLE_USER")
          this.isAdminOrModerator = false; 

        else
          this.router.navigate(['/login'])      
  }

}