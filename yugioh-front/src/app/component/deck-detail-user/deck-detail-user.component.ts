import { Component, ElementRef, OnInit, Renderer2, ViewChild, ViewEncapsulation } from '@angular/core';
import {CdkDragDrop, CdkDragEnter, CdkDragExit, CdkDragMove, copyArrayItem, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import { Card } from 'src/app/classes/Card';
import { CardServiceService } from 'src/app/service/card-service/card-service.service';
import { DeckService } from 'src/app/service/deck.service';
import { Deck } from 'src/app/classes/deck';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';
import { GenericTypeCard } from 'src/app/Util/enums/GenericTypeCards';
import { GeneralFunctions } from 'src/app/Util/GeneralFunctions';
import { DeckDetailUserService } from './deck-detail-user.service';


@Component({
  selector: 'app-deck-detail-user',
  templateUrl: './deck-detail-user.component.html',
  styleUrls: ['./deck-detail-user.component.css'],
  encapsulation: ViewEncapsulation.None,
})

export class DeckDetailUserComponent implements OnInit {
  @ViewChild('btnSpan',{static: false})span:ElementRef;
  @ViewChild('dropListContainer',{static: false}) dropListContainer?: ElementRef;

  constructor(private render: Renderer2, private cardService: CardServiceService, private deckService: DeckService, private deckDetailUSerService: DeckDetailUserService) { }

  dropListReceiverElement?: HTMLElement;
  dragDropInfo?: {
    dragIndex: number;
    dropIndex: number;
  };

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
      console.log(event.container.data)
      event.container.data.splice(event.previousIndex,1)
    }

    console.log(this.arrayCards, this.mainDeckCards)  
  }


  loadRandomCards(){
    this.arrayCards = [];

    this.deckDetailUSerService.randomCardsDetailed().subscribe( cards => {
      
      for(var i = 0; i < cards.length; i++){

        let card:Card = cards[i] ;

        Object.assign(cards[i], {isExtraDeck: null})

          if(card != null && card != undefined){

            let isExtraDeckCard = GeneralFunctions.isExtraDeckCard(card.generic_type);

            if(isExtraDeckCard)
              card.isExtraDeck = true;

            else {
                card.isExtraDeck = false
            }

            this.arrayCards.push(card)
          } 

         
       }

    
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
  
dragEntered(event: CdkDragEnter<number>) {
  const drag = event.item;
  const dropList = event.container;
  const dragIndex = drag.data;
  const dropIndex = dropList.data;

  this.dragDropInfo = { dragIndex, dropIndex };
  console.log('dragEntered', { dragIndex, dropIndex });

  const phContainer = dropList.element.nativeElement;
  const phElement = phContainer.querySelector('.cdk-drag-placeholder');

  if (phElement) {
    phContainer.removeChild(phElement);
    phContainer.parentElement.insertBefore(phElement, phContainer);

    moveItemInArray(this.mainDeckCards, dragIndex, dropIndex);
  }
}

dragMoved(event: CdkDragMove<number>) {
  if (!this.dropListContainer || !this.dragDropInfo) return;

  const placeholderElement =
    this.dropListContainer.nativeElement.querySelector(
      '.cdk-drag-placeholder'
    );

  const receiverElement =
    this.dragDropInfo.dragIndex > this.dragDropInfo.dropIndex
      ? placeholderElement.nextElementSibling
      : placeholderElement.previousElementSibling;

  if (!receiverElement) {
    return;
  }

  receiverElement.style.display = 'none';
  this.dropListReceiverElement = receiverElement;
}

dragDropped(event: CdkDragDrop<number>) {
  if (!this.dropListReceiverElement) {
    return;
  }

  this.dropListReceiverElement.style.removeProperty('display');
  this.dropListReceiverElement = undefined;
  this.dragDropInfo = undefined;
}

isShowTooltip: boolean = false;
imgTooltip: string;
topTp;
leftTp;
 //Serão enviadas para o tooltip
 cardImage:string;
 card:Card;

mostrarDivCardsInfo(e, cardNumber:any){

  this.leftTp =  e.pageX - 700 + "px";
  this.topTp = + e.pageY - 100 + "px";
  this.isShowTooltip = true;

  this.cardImage = GeneralFunctions.cardImagem + cardNumber + '.jpg';
  this.cardService.findByNumero(cardNumber).subscribe(card => { console.log(this.card); this.card = card  });

}

esconderImgToolTip(){
  this.isShowTooltip = false;
}

addCardSideDeck(index:any){

  this.sideDeckCards.unshift(this.arrayCards[index])
  console.log("o card foi " + this.mainDeckCards);

}

}


