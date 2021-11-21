import { Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import {CdkDragDrop, CdkDragExit, copyArrayItem, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import { Card } from 'src/app/classes/Card';
import { CardServiceService } from 'src/app/service/card-service/card-service.service';
import { DeckService } from 'src/app/service/deck.service';
import { Deck } from 'src/app/classes/deck';
import { THIS_EXPR } from '@angular/compiler/src/output/output_ast';


@Component({
  selector: 'app-deck-detail-user',
  templateUrl: './deck-detail-user.component.html',
  styleUrls: ['./deck-detail-user.component.css']
})
export class DeckDetailUserComponent implements OnInit {
  @ViewChild('btnSpan',{static: false})span:ElementRef;
  constructor(private render: Renderer2, private cardService: CardServiceService, private deckService: DeckService) { }

   card = {
      image:"/../../assets/img/outras/ex.jpg",
      name:"Morphin Jar",
      type: "Fiend"
  }

  card2 = {
    image:"/../../assets/img/outras/trapIcon.png",
    name:"Morphin Jar",
    type: "Fiend"
}

card3 = {
  image:"/../../assets/img/outras/linkIcon.png",
  name:"Morphin Jar",
  type: "Fiend"
}
  
arrayCards = new Array();
 
mainDeckCards: Deck[] = [];

extraDeckCards = new Array()

sideDeckCards = new Array()



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
  this.deckService.getDeckDetails(2,"User").subscribe(data => {
    this.mainDeckCards = data;
    this.extraDeckCards = data['extraDeck'];
    this.sideDeckCards = data['sideDeckCards'];
    //console.log(this.mainDeckCards)
  })
}

/*filterExtraDeck(data:Object){

      for (var i = data['cards'].length - 1; i >= 0; i--) {
      
      let generic = data['cards'][i].generic_type;

      if(generic == "SYNCHRO" || generic == "XYZ" || generic == "FUSION"){
        //Acrescenta no Array de Extra Deck
        this.extraDeckCards.push(data['cards'][i]);
        //Remove do Array de Main Deck
        data['cards'].splice(i, 1);
      }

    } 
    console.log(this.extraDeckCards)
  }*/


cardImagem(cardId: any){
  let urlimg = 'https://storage.googleapis.com/ygoprodeck.com/pics/' + cardId + '.jpg';
  return urlimg;
}
  


}


