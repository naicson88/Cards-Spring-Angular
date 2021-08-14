import { Cards } from "../classes/Rel_Deck_Cards";
import { CardServiceService } from "../service/card-service/card-service.service";


export abstract class GeneralFunctions  {

    public static cardImagem:string = 'https://storage.googleapis.com/ygoprodeck.com/pics/';

    public static relUserCards(cardsFound: Cards[], service: CardServiceService) {

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
}
