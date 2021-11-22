import { Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import {CdkDragDrop, CdkDragExit, copyArrayItem, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import { Card } from 'src/app/classes/Card';
import { CardServiceService } from 'src/app/service/card-service/card-service.service';
import { DeckService } from 'src/app/service/deck.service';
import { Deck } from 'src/app/classes/deck';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { GenericTypeCard } from 'src/app/Util/enums/GenericTypeCards';


@Component({
  selector: 'app-deck-detail-user',
  templateUrl: './deck-detail-user.component.html',
  styleUrls: ['./deck-detail-user.component.css']
})
export class DeckDetailUserComponent implements OnInit {
  @ViewChild('btnSpan',{static: false})span:ElementRef;
  constructor(private render: Renderer2, private cardService: CardServiceService, private deckService: DeckService) { }

   typeCard = {
     monster:0,
     magic:0,
     trap:0,
     synchro:0,
     xyz:0,
     fusion:0,
     link:0
   }
  
arrayCards = new Array();
 
mainDeckCards: Card[] = [];

extraDeckCards: Card[] = [];

sideDeckCards: Card[] = [];

  ngOnInit() {
     
    this.loadRandomCards();
    this.loadDeckCards();

  }

  drop(event: CdkDragDrop<String[]>) {

      if(event.previousContainer === event.container){
        moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
      } else {
        copyArrayItem(
          event.previousContainer.data,
          event.container.data,
          event.previousIndex,
          event.currentIndex,
        );
    }

    if(!event.isPointerOverContainer){
      event.container.data.splice(event.previousIndex,1)
    }

    console.log(this.arrayCards, this.mainDeckCards)  
  }

  btnSearch(event){
    
  }

  loadRandomCards(){
    this.arrayCards = [];

    this.cardService.randomCards().subscribe( data =>{
      this.arrayCards = data;
    })
}

loadDeckCards(){
    const id = localStorage.getItem("idDeckDetails");
    this.deckService.getDeckDetails(id,"User").subscribe(data => {
    this.mainDeckCards = data['cards'];
    this.countTypeCards(this.mainDeckCards, "main");
    this.extraDeckCards = data['extraDeck'];
    this.countTypeCards(this.extraDeckCards, "extra");
    this.sideDeckCards = data['sideDeckCards'];
    //this.sendCardsToArray( data['extraDeck'], data['extraDeck']);
  })
}

countTypeCards(data:Card[], deck:string){
      if(deck === 'main'){
       // console.log(data)
        this.typeCard.monster = data.filter(card => card.nivel != null).length;
        this.typeCard.magic = data.filter(card => card.generic_type === GenericTypeCard.SPELL).length;
        this.typeCard.trap = data.filter(card => card.generic_type === GenericTypeCard.TRAP).length;

      } else if (deck === 'extra'){
  
        this.typeCard.synchro = data.filter(card => card.generic_type === GenericTypeCard.SYNCHRO).length;
        this.typeCard.xyz = data.filter(card => card.generic_type === GenericTypeCard.XYZ).length;
        this.typeCard.fusion = data.filter(card => card.generic_type === GenericTypeCard.FUSION).length;
        this.typeCard.link = data.filter(card => card.generic_type === GenericTypeCard.LINK).length;

      } else {
        alert("It was not possible count some deck cards. :( ")
      }
  }


cardImagem(cardId: any){
  let urlimg = 'https://storage.googleapis.com/ygoprodeck.com/pics/' + cardId + '.jpg';
  return urlimg;
}
  


}


