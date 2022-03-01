import { Injectable, LOCALE_ID } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { Observable } from "rxjs";
import { User } from "src/app/classes/User";
import { AuthService } from "src/app/service/auth-service/auth.service";

 @Injectable({
    providedIn: 'root'
  })
  export class BaseRoleGuard implements CanActivate {

    isUserAdmin: boolean = false;
    user:User[] = [];
    constructor(private authService: AuthService, private router: Router) {}
   
     canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {

        this.checkUserRole();
        
        let role: string = localStorage.getItem("userRole");
        this.isUserAdmin = role == "ROLE_ADMIN" ? true: false;

        localStorage.removeItem("userRole");

        return this.isUserAdmin;

     }

    

  checkUserRole() {
        
      this.authService.getUser().subscribe(userReturned => { 
          
          let role:string = userReturned.roles[0].roleName

          if(role === "ROLE_ADMIN" || role === "ROLE_MODERATOR")
              localStorage.setItem('userRole', "ROLE_ADMIN");
          else
              localStorage.setItem('userRole', "ROLE_USER");

            }, error => {
              console.log("Error when try to consult user" + error.erro);
              this.router.navigate(['/error-page', 500])
            })
                
      }

    }