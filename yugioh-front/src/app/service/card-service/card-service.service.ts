
import { Inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Card } from 'src/app/classes/Card';
import { SearchCriteria } from 'src/app/classes/SearchCriteria';
import { Cards } from 'src/app/classes/Rel_Deck_Cards';
import { catchError } from 'rxjs/operators';
import { HandleErros } from 'src/app/Util/HandleErros';


@Injectable({
  providedIn: 'root'
})
export class CardServiceService {

  card: Card;

  constructor(private http: HttpClient) {}
  
  base_url = "http://localhost:8080/yugiohAPI"

  //head = new HttpHeaders().set('Authorization', `Bearer ${this.jwt.getToken()}`).set('Content-Type', 'application/json').set('Accept', 'application/json');

  public getCards(params){
    return  this.http.get<Card[]>(this.base_url+"/decks/todos")
    .pipe(
      catchError(HandleErros.handleError)
    )
  }

  public getCardDetails(id:any) {
    return this.http.get<any[]>(this.base_url+`/cards/number/${id}`)
    .pipe(
      catchError(HandleErros.handleError)
    )
  }

  public searchCards(criterios:SearchCriteria[]){
    return this.http.post<Cards[]>(this.base_url+"/cards/searchCard", criterios)
    .pipe(
      catchError(HandleErros.handleError)
    )
  }

  public randomCards(){
    return this.http.get<Cards[]>(this.base_url+"/cards/randomCards")
    .pipe(
      catchError(HandleErros.handleError)
    )
  }

  public relUserCards(cardsNumbers:number[]){
    return this.http.get<any[]>(this.base_url+`/cards/rel-user-cards?cardsNumbers=${cardsNumbers}`)
    .pipe(
      catchError(HandleErros.handleError)
    );
  }

}