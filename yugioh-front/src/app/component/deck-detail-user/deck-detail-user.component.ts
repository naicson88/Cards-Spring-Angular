import { AfterViewChecked, AfterViewInit, Component, ElementRef, HostListener, Inject, OnInit, Renderer2, ViewChild, ViewEncapsulation, ɵCodegenComponentFactoryResolver } from '@angular/core';
import { Card } from 'src/app/classes/Card';
import { CardServiceService } from 'src/app/service/card-service/card-service.service';
import { DeckService } from 'src/app/service/deck.service';
import { GenericTypeCard } from 'src/app/Util/enums/GenericTypeCards';
import { GeneralFunctions } from 'src/app/Util/GeneralFunctions';
import { DeckDetailUserService } from './deck-detail-user.service';
import {  ToastrService } from 'ngx-toastr';
import { Deck } from 'src/app/classes/deck';
import { MatDialog } from '@angular/material';
import { SearchBoxComponent } from '../cards-search/search-box/search-box.component';
import { BehaviorSubject, of } from 'rxjs';
import { ErrorDialogComponent } from '../dialogs/error-dialog/error-dialog.component';
import { WarningDialogComponent } from '../dialogs/warning-dialog/warning-dialog.component';
import { RelDeckCards } from 'src/app/classes/Rel_Deck_Cards';
import { SuccessDialogComponent } from '../dialogs/success-dialog/success-dialog.component';
import { error } from 'protractor';
import { Router } from '@angular/router';



@Component({
  selector: 'app-deck-detail-user',
  templateUrl: './deck-detail-user.component.html',
  styleUrls: ['./deck-detail-user.component.css'],
  encapsulation: ViewEncapsulation.None,
})

export class DeckDetailUserComponent implements OnInit, AfterViewInit, AfterViewChecked {
  @ViewChild('btnSpan',{static: false})span:ElementRef;
  @ViewChild('dropListContainer',{static: false}) dropListContainer?: ElementRef;
  @ViewChild('deckName', {static:false}) deckNome:ElementRef

  constructor(private cardService: CardServiceService, private ref: ElementRef, private router :Router,
    private deckService: DeckService, private deckDetailUSerService: DeckDetailUserService,  private toastr: ToastrService, public dialog: MatDialog) { }

  dropListReceiverElement?: HTMLElement;

  toManage = false;

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

  ngAfterViewInit (){
    this.setRarityClassAndPriceTitle()
  }

  ngAfterViewChecked() {
    
  }


  storedCardId(cardNumber:any){
  
      if(cardNumber != null && cardNumber != ""){
        this.cardService.setCardNumber(cardNumber);

      } else {
         console.log("Unable to consult this card, try again later.");
         return false;
      }
     
    }

loadDeckCards(){
  
  const id = localStorage.getItem("idDeckDetails");
  
  this.deckService.editDeck(id, "User").subscribe(data => {
 
  this.deck = data

  this.mainDeckCards = data['cards'];
  this.countTypeCards(this.mainDeckCards, "main");

  this.extraDeckCards = data['extraDeck'];
  this.countTypeCards(this.extraDeckCards, "extra");

  this.sideDeckCards = data['sideDeckCards'];
  this.relDeckCards =  data['rel_deck_cards'];
  this.calculateDeckPrice(this.relDeckCards);
  this.setRelDeckCards();

  //this.calculateQtdRarity();

  },
  error =>{
    let errorCode = error.status;
    this.router.navigate(["/error-page", errorCode]);
  })
}


  loadRandomCards(){
    this.arrayCards = [];
    
    this.deckDetailUSerService.randomCardsDetailed().subscribe( cards => {
      
      this.validTypeDeckCard(cards);
   
    },
    error =>{
      let errorCode = error.status;
      this.router.navigate(["/error-page", errorCode]);
    })
}

validTypeDeckCard(cards:any){
  
  for(var i = 0; i < cards.length; i++){

    let card:Card = cards[i] ;

    Object.assign(cards[i], {isExtraDeck: null})

      if(card != null && card != undefined){

        let isExtraDeckCard = GeneralFunctions.isExtraDeckCard(card.genericType);

        if(isExtraDeckCard)
          card.isExtraDeck = true;

        else {
            card.isExtraDeck = false
        }

        this.arrayCards.push(card)
      } 
   
   }
}

setRarityClassAndPriceTitle(){

  this.mainDeckCards.forEach((card, index) => {
    let hiddenRarity = document.getElementById('main_hidden_'+index)
    let hiddenPrice = document.getElementById('main_hidden_price_'+index);
    hiddenRarity.className = GeneralFunctions.rarity(card.raridade)   
    hiddenPrice.title = card.price.toFixed(2);
  });

  this.extraDeckCards.forEach((card, index) => {
    let hiddenRarity = document.getElementById('extra_hidden_'+index)
    let hiddenPrice = document.getElementById('extra_hidden_price_'+index);
    hiddenRarity.className = GeneralFunctions.rarity(card.raridade)   
    hiddenPrice.title = card.price.toFixed(2);
  });

  this.sideDeckCards.forEach((card, index) => {
    let hiddenRarity = document.getElementById('side_hidden_'+index)
    let hiddenPrice = document.getElementById('side_hidden_price_'+index);
    hiddenRarity.className = GeneralFunctions.rarity(card.raridade)   
    hiddenPrice.title = card.price.toFixed(2);
  });
  
  this.calculateQtdRarity();

}

countTypeCards(data:Card[], deck:string){

      if(deck === 'main'){
       // console.log(data)
        this.typeCard.monster = data.filter(card => card.nivel != null).length;
        this.typeCard.magic = data.filter(card => card.genericType === GenericTypeCard.SPELL).length;
        this.typeCard.trap = data.filter(card => card.genericType === GenericTypeCard.TRAP).length;

      } else if (deck === 'extra'){
  
        this.typeCard.synchro = data.filter(card => card.genericType === GenericTypeCard.SYNCHRO).length;
        this.typeCard.xyz = data.filter(card => card.genericType === GenericTypeCard.XYZ).length;
        this.typeCard.fusion = data.filter(card => card.genericType === GenericTypeCard.FUSION).length;
        this.typeCard.link = data.filter(card => card.genericType === GenericTypeCard.LINK).length;

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

  if(rel == undefined || rel == null){
    this.errorDialog("Sorry, some error happened, try again later!");
    return false;
  }

 let arr = []
 arr.push(rel)
 card.relDeckCards = arr;
 card.raridade = rel.card_raridade
 card.price = rel.card_price

 this.relDeckCards.splice(relIndex, 1); 
 
}

cardImagem(cardId: any){
  let urlimg = GeneralFunctions.cardImagem + cardId + '.jpg';
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
 this.sideDeckCards[0].relDeckCards.isSideDeck = true
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

removeFromArray(collection:Card[], index:any, typeDeck:string){

  try{

    collection.splice(index,1);

    if(typeDeck != 'side'){
      this.countTypeCards(collection, typeDeck);
    }
    
    this.changePriceAndRarity(typeDeck,index,true, null)
    this.toastr.info("Card removed from Deck.");
    
  }catch(e){
     if(e instanceof Error){
      alert("Sorry, can't remove card. Try again later :( ")
     }  
  }

    this.calculateQtdRarity();
    this.recalculateDeckPrice();
}

totalDeckValue:string

calculateDeckPrice(relDeckCards:any[]){
  let sum: number = 0;
  relDeckCards.forEach(card => sum += card.card_price);
  this.totalDeckValue = sum.toFixed(2)
}

 calculateQtdRarity(){
 
  this.deck.qtdCommon = document.getElementsByClassName('common').length  //this.deck['cards'].filter(rel => rel.raridade == 'Common').length;
  this.deck.qtdRare =  document.getElementsByClassName('rare').length //this.deck['cards'].filter(rel => rel.raridade == 'Rare').length;
  this.deck.qtdSuperRare = document.getElementsByClassName('super_rare').length// this.deck['cards'].filter(rel => rel.raridade == 'Super Rare').length;
  this.deck.qtdUltraRare = document.getElementsByClassName('ultra_rare').length

  this.recalculateDeckPrice();
 } 

 recalculateDeckPrice(){
   
   let unitPrices = document.querySelectorAll<HTMLElement>('.price');
   let price:number = 0;
  
   for(var i = 0; i < unitPrices.length; i++){
     try{
        if(!isNaN(Number(unitPrices[i].title))){
         let val = Number(unitPrices[i].title);
         price += val;
        }

     }catch(e){
       let error:Error = e;
        console.log(error.message);
        alert("Error, can't calculate deck price :(" );
        return false;
     }
   }

   this.totalDeckValue = price.toFixed(2);
 }

 criterias = new Array();
 openDialogSearch() {
  const dialogRef = this.dialog.open(SearchBoxComponent);

  dialogRef.afterClosed().subscribe(result => {
    
    if(result.data != null && result.data != undefined){
      this.arrayCards = [];
      this.page = 0;

      if(result.data.content.length > 0)
        this.validTypeDeckCard(result.data.content);
      else
        this.warningDialog("No Cards found in this consult")

      this.criterias = result.criterias
    }
  }, error => {
      this.toastr.error("Sorry, something bad happened, try again later. ERROR " + error.status)
  });
}

getRequestParam(pageSize, page){
  let params = {}

  if (page) {
    params[`page`] = page //- 1;
  }

  if (pageSize) {
    params[`size`] = pageSize;
  }

  return params;

}

onScroll(){
  
  let div =  document.getElementById('cardsSearch');
  let scrollY= div.scrollHeight - div.scrollTop;
  let height = div.offsetHeight
  let offset = height - scrollY;
  
  if (offset == 0 || ( offset > 0 && offset < 20)) {
    this.page = this.page + 1;
    const params = this.getRequestParam(this.pageSize, this.page)

    this.cardService.searchCardsDetailed(params, this.criterias).subscribe( newCards => {

      let arrCards:any = newCards.content;
     
      this.validTypeDeckCard(arrCards)
      

    }, error => {
      this.toastr.error("There was some error in consult cards. ERROR: " + error.status);
    })
}

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

    },
    error =>{
      console.log(error.body)
      this.errorDialog("ERROR: Something wrong happened, try again later.")
    }) 

  } 
 
}


updateCardSetCode(relationArray: RelDeckCards[], cardNumber:any){

  let cardMainDeck:Card[] = this.mainDeckCards.filter(card => card.numero == cardNumber)
  let cardExtraDeck:Card[] = this.extraDeckCards.filter(card => card.numero == cardNumber);
  let cardSideDeck:Card[] = this.sideDeckCards.filter(card => card.numero == cardNumber);

  if(cardMainDeck != null && cardMainDeck != undefined)  
  this.updateCardSetCodeInSpecificDeck(relationArray, cardMainDeck, false);

  if(cardExtraDeck != null && cardExtraDeck != undefined)
    this.updateCardSetCodeInSpecificDeck(relationArray, cardExtraDeck, false); 

  if(cardSideDeck != null && cardSideDeck != undefined)
    this.updateCardSetCodeInSpecificDeck(relationArray, cardSideDeck,true); 

    if(!this.mapSetCodes.has(cardNumber)){
      this.mapSetCodes.set(cardNumber, relationArray);
      }
  
}

updateCardSetCodeInSpecificDeck(relationArray:RelDeckCards[], cards:Card[], isSideDeck:boolean){

  cards.forEach(card => {
    card.relDeckCards = []
    relationArray.forEach(rel =>{
    rel.isSideDeck = isSideDeck == true ? true : false
    card.relDeckCards.push(rel)
    })
  })
}
    

onChangeCardSetCode(cardSetCode:string, array:string, index){

  if(cardSetCode == "0"){
    this.changePriceAndRarity(array, index, true, null);
    this.calculateQtdRarity();
    return false;
  }

 let typeArray = this.findTypeDeckArray(array);
 let card = typeArray[index];

 let rel:RelDeckCards =  card['relDeckCards'].find(set => set.card_set_code == cardSetCode)

 this.changePriceAndRarity(array, index,false, rel);

 this.calculateQtdRarity();

 this.recalculateDeckPrice();

}

changePriceAndRarity(array:String, index:string, isSetCodeZero:boolean, rel:RelDeckCards){
  
  let priceId = array+"_"+index;
  let rarityId =  array+"_r_"+index;
  let rarityCountId = array+"_hidden_"+index;
  let priceHidden = array+"_hidden_price_"+index;

  let liPrice = document.getElementById(priceId);
  let liRarity = document.getElementById(rarityId);
  let hiddenInputRarity = document.getElementById(rarityCountId);
  let hiddenInputPrice = document.getElementById(priceHidden);

  if(isSetCodeZero){
    liPrice.innerHTML ="$ 0.00";
    liPrice.style.color = 'red';
    liRarity.innerHTML = "-";
    hiddenInputRarity.className = "-";
    hiddenInputPrice.title = ""

  } else {
    
      liPrice.innerHTML="$ "+rel.card_price.toFixed(2);
      liPrice.style.color = '#228B22'
      liRarity.innerHTML=rel.card_raridade;
      //liRarity.className = rel.card_raridade;
      hiddenInputRarity.className = GeneralFunctions.rarity(rel.card_raridade)
      hiddenInputPrice.title = rel.card_price.toFixed(2);   
  }
     
}

findTypeDeckArray(array:string){

  if(array == 'main'){return this.mainDeckCards}
  else if (array == 'extra'){return this.extraDeckCards}
  else if(array == 'side'){return this.sideDeckCards}

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

successDialog(successMessage:string){
  this.dialog.open(SuccessDialogComponent,{
    data: successMessage
  })
}

sendToSideDeck(deckType:string, index:number){
    let deck:Card[] = this.findTypeDeckArray(deckType);
    let card:Card = deck[index];

    deck.splice(index,1);
    this.sideDeckCards.unshift(card);
    this.countTypeCards(deck, deckType);

    this.toastr.success("Card sent to Side Deck!")
}

sendToMainDeck(index:number){
  let card = this.sideDeckCards[index];
  Object.assign(card, {isExtraDeck: null})

  let isExtraDeck = GeneralFunctions.isExtraDeckCard(card.genericType);

  if(isExtraDeck){
    this.sideDeckCards.splice(index, 1);
    this.extraDeckCards.unshift(card);
    this.countTypeCards(this.extraDeckCards, 'extra');

    this.toastr.success("Card sent to Extra Deck");
  } else {
    this.sideDeckCards.splice(index, 1);
    this.mainDeckCards.unshift(card);
    this.countTypeCards(this.mainDeckCards, 'main');

    this.toastr.success("Card sent to Main Deck");
  }



}

relDeckCardsForSave:RelDeckCards[] = new Array();

saveDeck(){
  this.relDeckCardsForSave = [];

  let deckEdited:Deck = new Deck();

  deckEdited.id = this.deck.id;
  deckEdited.nome = this.deckNome.nativeElement.value.trim();
  deckEdited.setType = "D";
  
  let options = document.querySelectorAll('option:checked');

  this.insertInRelDeckCardForSave(this.mainDeckCards, 0, options, deckEdited.id, false);
  this.insertInRelDeckCardForSave(this.extraDeckCards, this.mainDeckCards.length, options, deckEdited.id, false);
  this.insertInRelDeckCardForSave(this.sideDeckCards, (this.mainDeckCards.length + this.extraDeckCards.length), options, deckEdited.id, true);

  deckEdited.rel_deck_cards = this.relDeckCardsForSave;
  
  if(deckEdited.nome == "" || deckEdited.nome == null){
    this.errorDialog("Deck name cannot be empty");
    return false;
  }
  


  this.deckService.saveUserDeck(deckEdited).subscribe(result => {
   // console.log("result " + JSON.stringify(result.headers))
      if(result.ok)
          this.successDialog("Deck was saved successfully!")
       
  }, error =>{
    console.log(JSON.stringify(error.body))
    this.errorDialog("Sorry, can't save deck now, try again later :(")
  })
}
errorMsg:string;
insertInRelDeckCardForSave(array:Card[], indexSum:number, options:NodeListOf<Element>, deckId:number, isSideDeck:boolean){
  
  for(var i = 0; i < array.length; i++){ 
    let rel:RelDeckCards = new RelDeckCards()  
    let setCode = options[i + indexSum].innerHTML

    if(setCode != "SET CODE..." && setCode != ""){
  
     // rel = array[i].relDeckCards.find(rel => rel.card_set_code = setCode);

      for(var j = 0; j < array[i].relDeckCards.length; j++){
        if(array[i].relDeckCards[j].card_set_code == setCode){
          rel = array[i].relDeckCards[j]
        }
      }
      //Need instantiate another object becaue typescript was replacing objects inside array
      if(rel != undefined && rel != null){
         let  rel2:RelDeckCards = new RelDeckCards()
         rel2.card_raridade = rel.card_raridade
         rel2.card_set_code = rel.card_set_code
         rel2.card_numero = rel.card_numero
         rel2.isSideDeck = isSideDeck
         rel2.id = rel.id
         rel2.card_price = rel.card_price
         rel2.deckId = deckId

          this.relDeckCardsForSave.push(rel2);

      }

    } else {
      let  rel2:RelDeckCards = new RelDeckCards()
      rel2.card_numero = array[i].numero
      rel2.isSideDeck = isSideDeck
      rel2.deckId = deckId
      this.relDeckCardsForSave.push(rel2);
    }   
  }
}

rearrengeCards(){
  try{
    this.rearrangeMain();
    this.rearrangeExtra();
    this.rearrangeSide();

    this.toastr.success("The cards have been rearranged.")
  } catch(e){

    if(e instanceof Error){
     alert("Sorry, can't rearrange cards, something bad happened. Try again later :( ")
    }  
 }
   
}

rearrangeMain(){
  let auxArray:Card[] = [];
 
  this.mainDeckCards.filter(card => card.genericType == GenericTypeCard.MONSTER).sort((a,b) => a.nome.localeCompare(b.nome)).forEach(card => auxArray.push(card));
  this.mainDeckCards.filter(card => card.genericType == GenericTypeCard.PENDULUM).sort((a,b) => a.nome.localeCompare(b.nome)).forEach(card => auxArray.push(card));
  this.mainDeckCards.filter(card => card.genericType == GenericTypeCard.SPELL).sort((a,b) => a.nome.localeCompare(b.nome)).forEach(card => auxArray.push(card));
  this.mainDeckCards.filter(card => card.genericType == GenericTypeCard.TRAP).sort((a,b) => a.nome.localeCompare(b.nome)).forEach(card => auxArray.push(card));
  this.mainDeckCards.filter(card => card.genericType == GenericTypeCard.TOKEN).sort((a,b) => a.nome.localeCompare(b.nome)).forEach(card => auxArray.push(card));
  this.mainDeckCards = [];
  this.mainDeckCards.push(...auxArray)
}

rearrangeExtra(){
  let auxArray:Card[] = [];

  this.extraDeckCards.filter(card => card.genericType == GenericTypeCard.FUSION).sort((a,b) => a.nome.localeCompare(b.nome)).forEach(card => auxArray.push(card));
  this.extraDeckCards.filter(card => card.genericType == GenericTypeCard.LINK).sort((a,b) => a.nome.localeCompare(b.nome)).forEach(card => auxArray.push(card));
  this.extraDeckCards.filter(card => card.genericType == GenericTypeCard.SYNCHRO).sort((a,b) => a.nome.localeCompare(b.nome)).forEach(card => auxArray.push(card));
  this.extraDeckCards.filter(card => card.genericType == GenericTypeCard.XYZ).sort((a,b) => a.nome.localeCompare(b.nome)).forEach(card => auxArray.push(card));

  this.extraDeckCards = [];
  this.extraDeckCards.push(...auxArray)
}

rearrangeSide(){
  let auxArray:Card[] = [];

  this.sideDeckCards.filter(card => card.genericType == GenericTypeCard.MONSTER).sort((a,b) => a.nome.localeCompare(b.nome)).forEach(card => auxArray.push(card));
  this.sideDeckCards.filter(card => card.genericType == GenericTypeCard.PENDULUM).sort((a,b) => a.nome.localeCompare(b.nome)).forEach(card => auxArray.push(card));
  this.sideDeckCards.filter(card => card.genericType == GenericTypeCard.SPELL).sort((a,b) => a.nome.localeCompare(b.nome)).forEach(card => auxArray.push(card));
  this.sideDeckCards.filter(card => card.genericType == GenericTypeCard.TRAP).sort((a,b) => a.nome.localeCompare(b.nome)).forEach(card => auxArray.push(card));
  this.sideDeckCards.filter(card => card.genericType == GenericTypeCard.TOKEN).sort((a,b) => a.nome.localeCompare(b.nome)).forEach(card => auxArray.push(card));
  this.sideDeckCards.filter(card => card.genericType == GenericTypeCard.FUSION).sort((a,b) => a.nome.localeCompare(b.nome)).forEach(card => auxArray.push(card));
  this.sideDeckCards.filter(card => card.genericType == GenericTypeCard.LINK).sort((a,b) => a.nome.localeCompare(b.nome)).forEach(card => auxArray.push(card));
  this.sideDeckCards.filter(card => card.genericType == GenericTypeCard.SYNCHRO).sort((a,b) => a.nome.localeCompare(b.nome)).forEach(card => auxArray.push(card));
  this.sideDeckCards.filter(card => card.genericType == GenericTypeCard.XYZ).sort((a,b) => a.nome.localeCompare(b.nome)).forEach(card => auxArray.push(card));

  this.sideDeckCards = [];
  this.sideDeckCards.push(...auxArray)
}
}



