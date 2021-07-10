
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CardDetailComponent } from 'src/app/component/card-detail/card-detail/card-detail.component';
import { Card } from 'src/app/module/Card';
import { Deck } from 'src/app/module/deck';


@Injectable({
  providedIn: 'root'
})
export class CardServiceService {

  card: Card;
  
  constructor(private http: HttpClient ) {}

  base_url = "http://localhost:8080/yugiohAPI"

  public getCards(params){
    return  this.http.get<Card[]>(this.base_url+"/decks/todos");
  }

  public getCardDetails(id:any) {
    return this.http.get<any[]>(this.base_url+`/cards/number/${id}`)
  }
}
