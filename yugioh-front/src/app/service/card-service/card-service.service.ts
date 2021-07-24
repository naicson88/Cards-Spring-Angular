
import { Inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { CardDetailComponent } from 'src/app/component/card-detail/card-detail/card-detail.component';
import { Card } from 'src/app/module/Card';
import { Deck } from 'src/app/module/deck';
import { SearchCriteria } from 'src/app/module/SearchCriteria';
import { Cards } from 'src/app/module/Rel_Deck_Cards';
import { JwtAuthStrategy } from '../auth-service/jwt-auth.strategy';
import { AUTH_STRATEGY } from '../auth-service/auth.strategy';


@Injectable({
  providedIn: 'root'
})
export class CardServiceService {

  card: Card;
  
  constructor(private http: HttpClient , @Inject(AUTH_STRATEGY) private jwt: JwtAuthStrategy) {}

  base_url = "http://localhost:8080/yugiohAPI"

  head = new HttpHeaders().set('Authorization', `Bearer ${this.jwt.getToken()}`).set('Content-Type', 'application/json').set('Accept', 'application/json');

  public getCards(params){
    return  this.http.get<Card[]>(this.base_url+"/decks/todos");
  }

  public getCardDetails(id:any) {
    return this.http.get<any[]>(this.base_url+`/cards/number/${id}`)
  }

  public searchCards(criterios:SearchCriteria[]){
    console.log(this.head)
    return this.http.post<Cards[]>(this.base_url+"/cards/searchCard", criterios);
  }

}
