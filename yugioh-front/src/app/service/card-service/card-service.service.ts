
import { Inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Card } from 'src/app/classes/Card';
import { SearchCriteria } from 'src/app/classes/SearchCriteria';
import { catchError } from 'rxjs/operators';
import { HandleErros } from 'src/app/Util/HandleErros';
import { Observable } from 'rxjs';
import { RelDeckCards } from 'src/app/classes/Rel_Deck_Cards';


@Injectable({
  providedIn: 'root'
})
export class CardServiceService {


  card: Card;
  private cardNumber: number;

  constructor(private http: HttpClient) {}
  
  base_url = "http://localhost:8080/yugiohAPI"

  //head = new HttpHeaders().set('Authorization', `Bearer ${this.jwt.getToken()}`).set('Content-Type', 'application/json').set('Accept', 'application/json');

  setCardNumber(cardNumber:number) {
     this.cardNumber = cardNumber;
  }

  getCardNumber(){
    return this.cardNumber;
  }

  public getCards(params){
    return  this.http.get<Card[]>(this.base_url+"/decks/todos")
    .pipe(
      catchError(HandleErros.handleError)
    )
  }

  public getCardsUserCollection(cardType: string){
      return this.http.get<Card[]>(this.base_url+`/cards/usercollection/${cardType}`)
      .pipe(
        catchError(HandleErros.handleError)
      )
  }

  public findByNumero(numero:any) {
    
    return this.http.get<Card>(this.base_url+`/cards/num/${numero}`)
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

  public searchCards(params:any, criterios:SearchCriteria[]){
    return this.http.post<Card[]>(this.base_url+`/cards/searchCard?size=${params.size}&page=${params.page}`, criterios)
    .pipe(
      catchError(HandleErros.handleError)
    )
  }

  
  public searchCardsDetailed(params:any, criterios:SearchCriteria[]){
    return this.http.post<any>(this.base_url+`/cards/searchCardDetailed?size=${params.size}&page=${params.page}`, criterios)
      .pipe(
        catchError(HandleErros.handleError)
      )
  }

  public randomCards(){
    return this.http.get<Card[]>(this.base_url+"/cards/randomCards")
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

  public getCardsByGenericType(params, genericType: string): Observable<any>{
    return  this.http.get<any[]>(this.base_url+`/cards/load-cards-userscollection?size=${params.size}&page=${params.page}&genericType=${genericType}`)
    .pipe(
      catchError(HandleErros.handleError)
    )
  } 

  public cardOfUserDetails(cardNumber:number) {
    return this.http.get<any>(this.base_url+`/cards/card-user-details?cardNumber=${cardNumber}`)
    .pipe(
        catchError(HandleErros.handleError)
      )
  }

  public searchCardsByName(cardName: string) {
    return this.http.get<any>(this.base_url+`/cards/cardname-usercollection?cardName=${cardName}`)
    .pipe(
      
      catchError(HandleErros.handleError)
    )
  }

  public findAllRelDeckCardsByCardNumber(cardNumber:any){
    return this.http.get<RelDeckCards[]>(this.base_url+`/cards/search-cardSetcodes?cardNumber=${cardNumber}`)
      .pipe(
        catchError(HandleErros.handleError)
      )
  } 

}
