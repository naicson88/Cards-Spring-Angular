import { HttpClient } from "@angular/common/http";
import { Observable, of } from "rxjs";
import { User } from "src/app/classes/User";

import { AuthStrategy } from "./auth.strategy";
import { configg } from "./config";

export class SessionAuthStrategy implements AuthStrategy<User> {
    private loggedUser: User;

    constructor(private http: HttpClient){}

    doLoginUser(user: User): void {
       this.loggedUser = user;
      
    }
    doLogoutUser(): void {
        this.loggedUser = undefined;
    }
    getCurrentUser(): Observable<User> {
       if(this.loggedUser){
           return of(this.loggedUser);
       }else {
           return this.http.get<User>(`${configg.authUrl}/User/${this.loggedUser.id}`).pipe(tap(user => this.loggedUser = user));
       }
    }
    
}

function tap(arg0: (user: any) => any): import("rxjs").OperatorFunction<User, User> {
    throw new Error("Function not implemented.");
}
