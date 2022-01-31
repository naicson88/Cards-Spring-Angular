import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Inject, Injectable } from "@angular/core";
import { Observable, throwError } from "rxjs";
import { AuthService } from "./auth.service";
import { AUTH_STRATEGY } from "./auth.strategy";
import { configg } from "./config";
import { JwtAuthStrategy } from "./jwt-auth.strategy";
import { catchError } from 'rxjs/operators';
import { Router } from "@angular/router";

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private router: Router, private authService: AuthService, @Inject(AUTH_STRATEGY) private jwt: JwtAuthStrategy) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    if (configg.auth === 'token' && this.jwt && this.jwt.getToken()) {
      request = this.addToken(request, this.jwt.getToken());
    }

     if(this.jwt.getToken() === null || this.jwt.getToken() === "") {
        this.authService.logout();
        this.router.navigate(['/index'])
     }

    return next.handle(request).pipe(catchError(error => {
      if (error.status === 401) {
        this.authService.doLogoutAndRedirectToLogin();
      }
       else if(error.status === 500){
        this.router.navigate(["/error-page", 500])
      }

      else if(error.status === 404){
        this.router.navigate(["error-page", 404])
      }
      return throwError(error);
    }));

  }

  private addToken(request: HttpRequest<any>, token: string) {
    return request.clone({
      setHeaders: { 'Authorization': `Bearer ${token}` }
    });
  }

}