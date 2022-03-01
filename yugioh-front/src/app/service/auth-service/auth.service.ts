import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { User } from 'src/app/classes/User';
import { AuthStrategy, AUTH_STRATEGY } from './auth.strategy';
import { LoginRequest } from 'src/app/classes/LoginRequest';
import { tap, map, catchError } from 'rxjs/operators';
import { JwtAuthStrategy } from './jwt-auth.strategy';
import { configg } from './config';



@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private readonly JWT_TOKEN = 'JWT_TOKEN';

  public readonly LOGIN_PATH ='/login';
  public readonly CONFIRM_PATH = '/confirm';
  public readonly INITIAL_PATH = '/home';

  userRole:string
  username: any;
  user: any;
  
  constructor(
  
    private router: Router,
    private http: HttpClient,
    @Inject(AUTH_STRATEGY) private auth: AuthStrategy<any>
  ) { }

  signup(user: User): Observable<void> {
    return this.http.post<any>(`http://localhost:8080/yugiohAPI/auth/signup`, user);
  }

  login(loginRequest: LoginRequest): Observable<User> {
    
    return this.http.post<any>(`${configg.authUrl}/auth/login`, loginRequest)
    .pipe(tap(user => {
        this.auth.doLoginUser(user)
       // localStorage.setItem('currentUser', JSON.stringify(user.roles[0]));
    }));

  }

  consultarUsuarioLogado(username: string){
    return  this.http.get<User>(`${configg.authUrl}/User/consulta-usuario/${username}`);
  }
  /*logout(){
    return this.http.get<any>(`${configg.authUrl}/auth/logout`)
      .pipe(tap(() => this.doLogoutUser()))
  }*/
  //Logout JWT
  logout(){
    localStorage.removeItem(this.JWT_TOKEN);
    localStorage.removeItem('currentUser');
  }

  private doLogoutUser() {
    this.auth.doLogoutUser();
  }

  isLoggedIn$(): Observable<boolean> {
    return this.auth.getCurrentUser().pipe(
      map(user => !!user),
      catchError(() => of(false))
    );
  }

  getCurrentUser$(): Observable<User>{
    return this.auth.getCurrentUser();
  }

  doLogoutAndRedirectToLogin() {
    this.doLogoutUser();
    this.router.navigate(['/login']);
  }

  getUser(): Observable<User>{
    
    this.getCurrentUser$().subscribe(user=>{
      this.username = user  
    })

    /*this.consultarUsuarioLogado(this.username.sub).subscribe(data =>{
      debugger
      this.user = data;  
      console.log("This user is " + this.user)  
    });*/

     return  this.user = this.consultarUsuarioLogado(this.username.sub);

  }

}



