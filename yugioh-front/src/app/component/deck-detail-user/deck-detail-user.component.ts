import { Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import {CdkDragDrop, CdkDragExit, copyArrayItem, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import { Card } from 'src/app/classes/Card';
import { CardServiceService } from 'src/app/service/card-service/card-service.service';


@Component({
  selector: 'app-deck-detail-user',
  templateUrl: './deck-detail-user.component.html',
  styleUrls: ['./deck-detail-user.component.css']
})
export class DeckDetailUserComponent implements OnInit {
  @ViewChild('btnSpan',{static: false})span:ElementRef;
  constructor(private render: Renderer2, private cardService: CardServiceService) { }

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
 
mainDeckCards = new Array(this.card)

extraDeckCards = new Array(this.card)

sideDeckCards = new Array()



  ngOnInit() {
     
    this.loadRandomCards()

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

cardImagem(cardId: any){
  let urlimg = 'https://storage.googleapis.com/ygoprodeck.com/pics/' + cardId + '.jpg';
  return urlimg;
}
  


}


