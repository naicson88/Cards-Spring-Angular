import { Serializer } from "@angular/compiler";
import { Card } from "../classes/Card";
import { CardServiceService } from "../service/card-service/card-service.service";
import { ExtraDeckTypes } from "./enums/ExtraDeckTypes";


export abstract class GeneralFunctions  {

    public static cardImagem:string = 'https://storage.googleapis.com/ygoprodeck.com/pics/';

    public static relUserCards(cardsFound: Card[], service: CardServiceService) {

        let cardNumbers = [];
        let relUserCard:any[];

        for(var i = 0; i < cardsFound.length; i++){
            if(cardsFound[i]['numero'] != null){cardNumbers.push(cardsFound[i]['numero'] )}
           }
           
            service.relUserCards(cardNumbers).subscribe(rel =>{
            relUserCard = rel;
          
            cardsFound.forEach( comp => {
              relUserCard.map( e => {
                if(e.cardNumero === comp.numero){
                  Object.assign(comp, {"qtd": e.qtd})
                }
              })
            })
            return relUserCard;
          });         
    }

    public static isExtraDeckCard(cardType:string) {
      
      if((<any>Object).values(ExtraDeckTypes).includes(cardType))
          return true;
      else
          return false;
    }

}
