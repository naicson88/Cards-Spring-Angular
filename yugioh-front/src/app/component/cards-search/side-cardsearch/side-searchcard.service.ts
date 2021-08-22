import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Card } from 'src/app/classes/Card';
import { GeneralFunctions } from 'src/app/Util/GeneralFunctions';
import { HandleErros } from 'src/app/Util/HandleErros';

@Injectable({
  providedIn: 'root'
})
export class SideSearchcardService {

  constructor(private http: HttpClient) { }

  base_url = "http://localhost:8080/yugiohAPI";
  

  public findCardToAddToUserCollection(cardNumber: number) {
      return this.http.get<any>(this.base_url + `/cards/add-card-to-user?cardNumber=${cardNumber}` )
        .pipe(
          catchError(HandleErros.handleError)
        )
  }

}
