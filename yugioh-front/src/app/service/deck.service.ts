import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Deck } from '../classes/deck';
import { Cards } from '../classes/Rel_Deck_Cards';
import { Observable } from 'rxjs';
import { HandleErros } from '../Util/HandleErros';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class DeckService {
  deck: Deck

  constructor(private http: HttpClient ) {}

  base_url = "http://localhost:8080/yugiohAPI"

 /*  public getDecks(params){
    return  this.http.get<Deck[]>(this.base_url+"/decks/todos");
  } */

    public getDecks(params, set_type:string): Observable<any>{
      debugger
    return  this.http.get(this.base_url+`/decks/pagination?size=${params.size}&page=${params.page}&setType=${set_type}`)
    .pipe(
      catchError(HandleErros.handleError)
    )
  } 

  public getDeckDetails(id:any) {
    return this.http.get<Deck[]>(this.base_url+`/decks/${id}`)
    .pipe(
      catchError(HandleErros.handleError)
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

}
