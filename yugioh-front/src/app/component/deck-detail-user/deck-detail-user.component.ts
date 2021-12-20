import { Component, ElementRef, HostListener, OnInit, Renderer2, ViewChild, ViewEncapsulation } from '@angular/core';
import {CdkDragDrop, CdkDragEnter, CdkDragExit, CdkDragMove, copyArrayItem, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import { Card } from 'src/app/classes/Card';
import { CardServiceService } from 'src/app/service/card-service/card-service.service';
import { DeckService } from 'src/app/service/deck.service';
import { GenericTypeCard } from 'src/app/Util/enums/GenericTypeCards';
import { GeneralFunctions } from 'src/app/Util/GeneralFunctions';
import { DeckDetailUserService } from './deck-detail-user.service';
import { ToastrService } from 'ngx-toastr';
import { Deck } from 'src/app/classes/deck';
import { MatDialog } from '@angular/material';
import { SearchBoxComponent } from '../cards-search/search-box/search-box.component';
import { BehaviorSubject } from 'rxjs';
import { ErrorDialogComponent } from '../dialogs/error-dialog/error-dialog.component';
import { WarningDialogComponent } from '../dialogs/warning-dialog/warning-dialog.component';
import { RelDeckCards } from 'src/app/classes/Rel_Deck_Cards';


@Component({
  selector: 'app-deck-detail-user',
  templateUrl: './deck-detail-user.component.html',
  styleUrls: ['./deck-detail-user.component.css'],
  encapsulation: ViewEncapsulation.None,
})

export class DeckDetailUserComponent implements OnInit {
  @ViewChild('btnSpan',{static: false})span:ElementRef;
  @ViewChild('dropListContainer',{static: false}) dropListContainer?: ElementRef;

  constructor(private render: Renderer2, private cardService: CardServiceService, 
    private deckService: DeckService, private deckDetailUSerService: DeckDetailUserService,  private toastr: ToastrService, public dialog: MatDialog) { }

  dropListReceiverElement?: HTMLElement;

  manage = false;

  cardsFromScroll = new BehaviorSubject([]);
  arrCardsFromScroll = new Array();
  page: number = 1; 
  pageSize: number = 30;


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

deck:Deck 

arrayCards = new Array();
 
mainDeckCards: Card[] = [];

extraDeckCards: Card[] = [];

sideDeckCards: Card[] = [];

relDeckCards: RelDeckCards[] = [];

  ngOnInit() {
    this.loadDeckCards();
    this.loadRandomCards();
   

  }

  loadDeckCards(){
    const id = localStorage.getItem("idDeckDetails");
    
    this.deckService.getDeckDetails(id,"User").subscribe(data => {
    console.log(data)
   
    this.deck = data
    console.log("deck: " + this.deck)
    this.mainDeckCards = data['cards'];
    this.countTypeCards(this.mainDeckCards, "main");

    this.extraDeckCards = data['extraDeck'];
    this.countTypeCards(this.extraDeckCards, "extra");

    this.sideDeckCards = data['sideDeckCards'];
    //this.sendCardsToArray( data['extraDeck'], data['extraDeck']);
    this.relDeckCards =  data['rel_deck_cards'];

    this.calculateDeckPrice(this.relDeckCards);

    this.setRelDeckCards();

    this.calculateQtdRarity();

  })
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
  }


  loadRandomCards(){
    this.arrayCards = [];

    this.deckDetailUSerService.randomCardsDetailed().subscribe( cards => {
      
      this.validTypeDeckCard(cards);
   
    })
}

validTypeDeckCard(cards:any){
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

/*setRelDeckCards(){

    this.mainDeckCards.forEach((card, index) => {

     let rel = this.relDeckCards.find(rel => rel.card_numero === card.numero);
     
     card.price = rel.card_price;
     card.raridade = rel.card_raridade;
     let str: string[] = []
     str.push(rel.card_set_code)
     card.set_code = str;

    })
}*/

setRelDeckCards(){

  this.mainDeckCards.forEach((card, index) => {

   let rel = this.relDeckCards.find(rel => rel.card_numero === card.numero);

  card.price = rel.card_price;
  card.relDeckCards = rel
  let str: string[] = []
  str.push(rel.card_set_code)
  card.set_code = str;

  });
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
  this.cardService.findByNumero(cardNumber).subscribe(card => { this.card = card  });

}

esconderImgToolTip(){
  this.isShowTooltip = false;
}

addCardSideDeck(index:any){

  this.sideDeckCards.unshift(this.arrayCards[index])
  this.toastr.success('Card added in Side Deck');

}

addCardExtraDeck(index:any){

  if(this.extraDeckCards.length >= 15){
    this.toastr.error("Extra Deck already have 15 cards, can't add more")
    return false;
 }

 let isLimitOver:boolean = this.isCardLimitOver(this.arrayCards[index], 'E')

  if(!isLimitOver){
    this.extraDeckCards.unshift(this.arrayCards[index]);
    this.toastr.success("Card added in Extra Deck");
    this.countTypeCards(this.extraDeckCards, 'extra');
  } else{
    this.toastr.warning("There are already three copies of this card")
  }
 
}

addCardMainDeck(index:any){

  if(this.mainDeckCards.length >= 60){
     this.toastr.error("Deck already have 60 cards, can't add more")
     return false;
  }

  let isLimitOver:boolean = this.isCardLimitOver(this.arrayCards[index], 'M')
 
  if(!isLimitOver){
    this.mainDeckCards.unshift(this.arrayCards[index]);
    this.toastr.success("Card added in Main Deck");
    this.countTypeCards(this.mainDeckCards, 'main');
  } else{
    this.toastr.warning("There are already three copies of this card")
  }

}

isCardLimitOver(cardAdded:any, tipoDeck:string){

    let qtdCards = 0;
    let deckType = tipoDeck == 'M' ? this.mainDeckCards : this.extraDeckCards;
    
    for(let card of deckType){
      if(card.numero === cardAdded.numero)
        qtdCards++;
  }

   if(qtdCards >= 3) {return true} else {return false} 

}

removeFromArray(collection:any[], index:any, typeDeck:string){

  try{

    collection.splice(index,1);
    this.toastr.info("Card removed from Deck.")
    this.countTypeCards(collection, typeDeck);

  }catch{
      console.error
      alert("Sorry, can't remove card. Try again later :( ")
  }
}

totalDeckValue:string

calculateDeckPrice(relDeckCards:any[]){
    let sum: number = 0;
    
    relDeckCards.forEach(card => sum += card.card_price);
    this.totalDeckValue = sum.toFixed(2)
}

 calculateQtdRarity(){

  this.deck.qtdCommon = this.deck['cards'].filter(rel => rel.raridade == 'Common').length;
  this.deck.qtdRare = this.deck['cards'].filter(rel => rel.raridade == 'Rare').length;
  this.deck.qtdSuperRare = this.deck['cards'].filter(rel => rel.raridade == 'Super Rare').length;
  this.deck.qtdUltraRare = this.deck['cards'].filter(rel => rel.raridade == "Ultra Rare").length; 

 } 

 criterias = new Array();
 openDialogSearch() {
  const dialogRef = this.dialog.open(SearchBoxComponent);

  dialogRef.afterClosed().subscribe(result => {
    if(result.data != null && result.data != undefined){
      this.arrayCards = [];
      this.validTypeDeckCard(result.data.content);
      this.criterias = result.criterias
    }
   

  });
}

getRequestParam(pageSize, page){
  let params = {}

  if (page) {
    params[`page`] = page - 1;
  }

  if (pageSize) {
    params[`size`] = pageSize;
  }

  return params;

}

onScroll(){
  const params = this.getRequestParam(this.pageSize, this.page)

  this.cardService.searchCardsDetailed(params, this.criterias).subscribe( newCards => {
    console.log(newCards);
    
    this.page = this.page + 1;
  })
}

cardsSearched = [];
consultCardSetCode(cardNumber:any){
    
  if(cardNumber == null || cardNumber == undefined){
    this.errorDialog("Sorry, can't consult card's set codes.");
    return false;
  }

  let isSeached = this.cardsSearched.includes(cardNumber,0);

  if(!isSeached){

    this.cardService.findAllRelDeckCardsByCardNumber(cardNumber).subscribe(data => {      
      let relationArray = data; 
     
      this.updateCardSetCode(relationArray, cardNumber)
      this.cardsSearched.push(cardNumber);

      this.onChangeCardSetCode("0", null)
    }) 

  }
 
}

updateCardSetCode(relationArray: RelDeckCards[], cardNumber:any){

  let cardMainDeck:Card = this.mainDeckCards.find(card => card.numero == cardNumber);
  let cardExtraDeck:Card = this.extraDeckCards.find(card => card.numero == cardNumber);
  let cardSideDeck:Card = this.extraDeckCards.find(card => card.numero == cardNumber);

  if(cardMainDeck != null && cardMainDeck != undefined)  
    this.updateCardSetCodeInSpecifDeck(relationArray, cardMainDeck);

  if(cardExtraDeck != null && cardExtraDeck != undefined)
    this.updateCardSetCodeInSpecifDeck(relationArray, cardExtraDeck); 

  if(cardSideDeck != null && cardSideDeck != undefined)
    this.updateCardSetCodeInSpecifDeck(relationArray, cardSideDeck); 
  
}

updateCardSetCodeInSpecifDeck(relationArray:RelDeckCards[], card:Card){

  card.relDeckCards = relationArray;

  let str: string[] = []
  relationArray.forEach(rel => {   
    str.push(rel.card_set_code)
  })
  card.set_code = str
}

onChangeCardSetCode(cardSetCode:string, card:Card){
 debugger
  if(cardSetCode == "0"){
    card.price = 0.00
    return false;
  }

 let rel =  card['relDeckCards'].find(set => set.card_set_code == cardSetCode)

 if(card.price != rel.card_price){this.singleCalculateDeckValue(card.price, rel.card_price)}

 card.price = rel.card_price;

 let relIndex = this.relDeckCards.map(function(set){ return set.card_set_code}).indexOf(cardSetCode);

  console.log("index  " + relIndex)
 if(relIndex != null && relIndex != undefined){
    this.relDeckCards.splice(relIndex, 1);
    this.relDeckCards.push(rel);

 }
}

singleCalculateDeckValue(currentCardValue, newCardValue){
    let total = Number(this.totalDeckValue);

    if( total > 0){
      let val = total - currentCardValue;
      val = val + newCardValue;

      this.totalDeckValue = val.toFixed(2);
    }

   
}

errorDialog(errorMessage:string){
  this.dialog.open(ErrorDialogComponent, {
    data: errorMessage
  })
}

warningDialog(warningMessage:string){
  this.dialog.open(WarningDialogComponent, {
    data: warningMessage
  })
}

}


