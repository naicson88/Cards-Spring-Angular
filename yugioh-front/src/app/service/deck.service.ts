import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Deck } from '../classes/deck';
import { Observable, throwError } from 'rxjs';
import { HandleErros } from '../Util/HandleErros';
import { catchError } from 'rxjs/operators';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';


@Injectable({
  providedIn: 'root'
})
export class DeckService {
  deck: Deck

  constructor(private http: HttpClient, private router: Router ) {}

  base_url = "http://localhost:8080/yugiohAPI"

 /*  public getDecks(params){
    return  this.http.get<Deck[]>(this.base_url+"/decks/todos");
  } */

    public getDecks(params, set_type:string): Observable<any>{
      if(set_type == "D" || set_type == "T" || set_type == "B"){ // Se for Konami set
         
        return  this.http.get(this.base_url+`/decks/pagination?size=${params.size}&page=${params.page}&setType=${set_type}`)
        .pipe(
          catchError(HandleErros.handleError)
        )
      } else if (set_type == "UD" || set_type == "UT" || set_type == "UB"){ //Se forem sets do usuário
            set_type.substr(1);
          return  this.http.get(this.base_url+`/decks/sets-of-user?size=${params.size}&page=${params.page}&setType=${set_type}`)
          .pipe(
            catchError(HandleErros.handleError)
          )
      }
   
  } 

  public getDeckDetails(id:any, deckType:string) {

    return this.http.get<Deck>(this.base_url+`/decks?id=${id}&deckType=${deckType}`) 
    .pipe(
      catchError(this.handleError)
    )
     
  }

  public addSetToUsersCollection(setId:number){
    return this.http.get<any>(this.base_url+`/decks/add-deck-to-user-collection/${setId}`)
    .pipe(
      catchError(HandleErros.handleError)
    )
  }

  public removeSetToUsersCollection(setId:number){
    return this.http.get<any>(this.base_url+`/decks/remove-set-to-user-collection/${setId}`)
    .pipe(
      catchError(HandleErros.handleError)
    )
  }

  public manegeDeckAndCardsOfUserCollection(deckId: number, flagAddOrRemove:string){
    return this.http.get<any>(this.base_url+`/decks/add-deck-to-user-collection/${deckId}/${flagAddOrRemove}`)
    .pipe(
      catchError(HandleErros.handleError)
    )
  }

  public relUserDeck(decksIds:number[]) {
    return this.http.get<any>(this.base_url+`/decks/rel-user-decks?decksIds=${decksIds}`)
    .pipe(
      catchError(HandleErros.handleError)
    )
  }

  public saveUserDeck(deck:Deck): Observable<HttpResponse<Deck>> {
    return this.http.post<Deck>(this.base_url+`/decks/save-userdeck`, deck, {observe:'response'}).pipe(
      catchError(HandleErros.handleError)
    )
  }

  public  handleError(error: HttpErrorResponse) {
    
   // const router = AppModule.injector.get(Router);
    if(error.error instanceof ErrorEvent){
        console.log(" An error occurred", error.error.message);
    } else {
        console.log(`Backend returned code  ${error.status} ` + ` body was: ${JSON.stringify(error.error)} `); 
       //this.router.navigateByUrl('/error-code')          
    }
  
    return throwError(error);
}

}
