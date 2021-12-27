import { AfterViewInit, Component, ElementRef, HostListener, Inject, OnInit, Renderer2, ViewChild, ViewEncapsulation } from '@angular/core';
import {CdkDragDrop, CdkDragEnter, CdkDragExit, CdkDragMove, copyArrayItem, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import { Card } from 'src/app/classes/Card';
import { CardServiceService } from 'src/app/service/card-service/card-service.service';
import { DeckService } from 'src/app/service/deck.service';
import { GenericTypeCard } from 'src/app/Util/enums/GenericTypeCards';
import { GeneralFunctions } from 'src/app/Util/GeneralFunctions';
import { DeckDetailUserService } from './deck-detail-user.service';
import { ToastrComponentlessModule, ToastrService } from 'ngx-toastr';
import { Deck } from 'src/app/classes/deck';
import { MatDialog } from '@angular/material';
import { SearchBoxComponent } from '../cards-search/search-box/search-box.component';
import { BehaviorSubject } from 'rxjs';
import { ErrorDialogComponent } from '../dialogs/error-dialog/error-dialog.component';
import { WarningDialogComponent } from '../dialogs/warning-dialog/warning-dialog.component';
import { RelDeckCards } from 'src/app/classes/Rel_Deck_Cards';
import { DOCUMENT } from '@angular/common';


@Component({
  selector: 'app-deck-detail-user',
  templateUrl: './deck-detail-user.component.html',
  styleUrls: ['./deck-detail-user.component.css'],
  encapsulation: ViewEncapsulation.None,
})

export class DeckDetailUserComponent implements OnInit, AfterViewInit {
  @ViewChild('btnSpan',{static: false})span:ElementRef;
  @ViewChild('dropListContainer',{static: false}) dropListContainer?: ElementRef;

  constructor(private cardService: CardServiceService, private ref: ElementRef,
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

mapSetCodes: Map<number, RelDeckCards[]> = new Map();

cardsSearched = []; // Guarda o numero dos cards que ja tiveram Setcode consultados

  ngOnInit() {
    this.loadDeckCards();
    this.loadRandomCards();
  }

  ngAfterViewInit(){
    this.calculateQtdRarity()
  }


  loadDeckCards(){
    const id = localStorage.getItem("idDeckDetails");
    
    this.deckService.getDeckDetails(id,"User").subscribe(data => {
   
    this.deck = data

    this.mainDeckCards = data['cards'];
    this.countTypeCards(this.mainDeckCards, "main");

    this.extraDeckCards = data['extraDeck'];
    this.countTypeCards(this.extraDeckCards, "extra");

    this.sideDeckCards = data['sideDeckCards'];
    //this.sendCardsToArray( data['extraDeck'], data['extraDeck']);
    this.relDeckCards =  data['rel_deck_cards'];

    this.calculateDeckPrice(this.relDeckCards);

    this.setRelDeckCards();

    //this.calculateQtdRarity();

  })
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

setRelDeckCards(){
  this.mainDeckCards.forEach((card) => {
      this.setRelDeckCardsTypeDeck(card);
   });

  this.extraDeckCards.forEach((card) => {
    this.setRelDeckCardsTypeDeck(card);
  }) 

  this.sideDeckCards.forEach((card) => {
    this.setRelDeckCardsTypeDeck(card);
  }) 

}

setRelDeckCardsTypeDeck(card:Card){
    
  let rel = this.relDeckCards.find(rel => rel.card_numero === card.numero);
  let relIndex = this.relDeckCards.findIndex(rel => rel.card_numero === card.numero);

 let arr = []
 arr.push(rel)
 card.relDeckCards = arr;
 card.raridade = rel.card_raridade
 this.relDeckCards.splice(relIndex, 1); 

}



cardImagem(cardId: any){
  let urlimg = 'https://storage.googleapis.com/ygoprodeck.com/pics/' + cardId + '.jpg';
  return urlimg;
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

  if(this.sideDeckCards.length >= 15){
    this.toastr.error("Side Deck already have 15 cards")
    return false;
 }

 this.validAndAddCardRespectiveDeck(index, this.sideDeckCards,"Card added in Side Deck", null)
 /*
  let isLimitOver:boolean = this.isCardLimitOver(this.arrayCards[index], this.sideDeckCards)

  if(!isLimitOver){
    let card:Card = this.arrayCards[index]
    card.relDeckCards = [];

    this.sideDeckCards.unshift(card)
    this.toastr.success('Card added in Side Deck');
    
  } else {
    this.toastr.warning("There are already three copies of this card")
  }
*/
}



addCardExtraDeck(index:any){

  if(this.extraDeckCards.length >= 15){
    this.toastr.error("Extra Deck already have 15 cards, can't add more")
    return false;
 }

 this.validAndAddCardRespectiveDeck(index, this.extraDeckCards, "Card added in Extra Deck", 'extra');
/*
 let isLimitOver:boolean = this.isCardLimitOver(this.arrayCards[index], this.extraDeckCards)

  if(!isLimitOver){
    let card:Card = this.arrayCards[index]
    card.relDeckCards = [];

    this.extraDeckCards.unshift(card);
    this.toastr.success("Card added in Extra Deck");
    this.countTypeCards(this.extraDeckCards, 'extra');
  } else{
    this.toastr.warning("There are already three copies of this card")
  }
 */
}

addCardMainDeck(index:any){

  if(this.mainDeckCards.length >= 60){
     this.toastr.error("Deck already have 60 cards")
     return false;
  }

  this.validAndAddCardRespectiveDeck(index, this.mainDeckCards,"Card added in Main Deck", 'main');
  /*
  let isLimitOver:boolean = this.isCardLimitOver(this.arrayCards[index], this.mainDeckCards)
 
  if(!isLimitOver){
   let card:Card = this.arrayCards[index]
   card.relDeckCards = [];

    this.mainDeckCards.unshift(card);
    this.toastr.success("Card added in Main Deck");
    this.countTypeCards(this.mainDeckCards, 'main');
    this.verifyMapSearchedCards(card);
  } else{
    this.toastr.warning("There are already three copies of this card")
  }*/
}

validAndAddCardRespectiveDeck(index, arrayDeck:Card[], messageToastr:string, typeDeck:string ){

  let isLimitOver:boolean = this.isCardLimitOver(this.arrayCards[index], arrayDeck)

  if(!isLimitOver){
    let card:Card = this.arrayCards[index]
    card.relDeckCards = [];
    
    arrayDeck.unshift(card)
    this.toastr.success(messageToastr);
    this.verifyMapSearchedCards(card);

    if(typeDeck != null && typeDeck != undefined){
      this.countTypeCards(this.mainDeckCards, 'main');  
    }
      
  } else {
    this.toastr.warning("There are already three copies of this card")
  }

}

verifyMapSearchedCards(card:Card){
  
  let rel = this.mapSetCodes.get(card.numero);

  if(rel != null && rel != undefined){
      card.relDeckCards = rel;
  }
}

isCardLimitOver(cardAdded:any, array:Card[]){

    let qtdCards = 0;
    let deckType = array
    
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
  debugger
 
  this.deck.qtdCommon = document.getElementsByClassName('Common').length  //this.deck['cards'].filter(rel => rel.raridade == 'Common').length;
  this.deck.qtdRare =  document.getElementsByClassName('Rare').length //this.deck['cards'].filter(rel => rel.raridade == 'Rare').length;
  this.deck.qtdSuperRare = document.getElementsByClassName('Super').length// this.deck['cards'].filter(rel => rel.raridade == 'Super Rare').length;
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

    }) 

  } 
 
}


updateCardSetCode(relationArray: RelDeckCards[], cardNumber:any){

  let cardMainDeck:Card[] = this.mainDeckCards.filter(card => card.numero == cardNumber)
  let cardExtraDeck:Card[] = this.extraDeckCards.filter(card => card.numero == cardNumber);
  let cardSideDeck:Card[] = this.sideDeckCards.filter(card => card.numero == cardNumber);

  if(cardMainDeck != null && cardMainDeck != undefined)  
  this.updateCardSetCodeInSpecificDeck(relationArray, cardMainDeck);

  if(cardExtraDeck != null && cardExtraDeck != undefined)
    this.updateCardSetCodeInSpecificDeck(relationArray, cardExtraDeck); 

  if(cardSideDeck != null && cardSideDeck != undefined)
    this.updateCardSetCodeInSpecificDeck(relationArray, cardSideDeck); 

    if(!this.mapSetCodes.has(cardNumber)){
      this.mapSetCodes.set(cardNumber, relationArray);
      }
  
}

updateCardSetCodeInSpecificDeck(relationArray:RelDeckCards[], cards:Card[]){

  cards.forEach(card => {
    card.relDeckCards = []
    relationArray.forEach(rel =>{
    card.relDeckCards.push(rel)
    })
  })
}
    

onChangeCardSetCode(cardSetCode:string, array:string, index){

  if(cardSetCode == "0"){
    this.changePriceAndRarity("", "0", true, null);
    return false;
  }

 let typeArray = this.findTypeDeckArray(array);
 let card = typeArray[index];

 let rel:RelDeckCards =  card['relDeckCards'].find(set => set.card_set_code == cardSetCode)

 this.changePriceAndRarity(array, index,false, rel);
 
 if(card.price != rel.card_price){this.singleCalculateDeckValue(card.price, rel.card_price)}

 this.calculateQtdRarity()

}

changePriceAndRarity(array:String, index:string, isSetCodeZero:boolean, rel:RelDeckCards){
  let priceId = array+"_"+index;
  let rarityId =  array+"_r_"+index;
  let rarityCountId = array+"_hidden_"+index;

  let liPrice = document.getElementById(priceId);
  let liRarity = document.getElementById(rarityId);
  let hiddenInputRarity = document.getElementById(rarityCountId);

  if(isSetCodeZero){
    liPrice.innerHTML ="$ 0.00";
    liRarity.innerHTML = "-";
    hiddenInputRarity.className = "";

  } else {
      liPrice.innerHTML="$ "+rel.card_price.toFixed(2);
      liRarity.innerHTML=rel.card_raridade;
      //liRarity.className = rel.card_raridade;
      hiddenInputRarity.className = rel.card_raridade;
     
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

findTypeDeckArray(array:string){

  if(array == 'main'){return this.mainDeckCards}
  else if (array = 'extra'){return this.extraDeckCards}
  else if(array = 'side'){return this.sideDeckCards}

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



