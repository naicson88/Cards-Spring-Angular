import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Deck } from '../module/deck';
import { Cards } from '../module/Rel_Deck_Cards';
import { Observable } from 'rxjs';

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

    public getDecks(params): Observable<any>{
    return  this.http.get(this.base_url+`/decks/pagination?size=${params.size}&page=${params.page}`);
  } 

  public getDeckDetails(id:any) {
    return this.http.get<Deck[]>(this.base_url+`/decks/${id}`)
  }

  public manegeDeckAndCardsOfUserCollection(deckId: number, flagAddOrRemove:string){
    return this.http.get<any>(this.base_url+`/decks/add-deck-to-user-collection/${deckId}/${flagAddOrRemove}`);
  }

  public relUserDeck(decksIds:number[]) {
    return this.http.get<any>(this.base_url+`/decks/rel-user-decks?decksIds=${decksIds}`)
  }

}
