import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError } from "rxjs/operators";
import { Card } from "src/app/classes/Card";
import { HandleErros } from "src/app/Util/HandleErros";

@Injectable({
    providedIn: 'root'
  })
  export class DeckDetailUserService {
  
  
    card: Card;
    private cardNumber: number;
  
    constructor(private http: HttpClient) {}
    
    base_url = "http://localhost:8080/yugiohAPI";

    public randomCardsDetailed(){
        return this.http.get<Card[]>(this.base_url+"/cards/randomCardsDetailed")
        .pipe(
          catchError(HandleErros.handleError)
        )
      }

  }