import { Component, OnInit } from '@angular/core';
import {CdkDragDrop, CdkDragExit, copyArrayItem, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import { Card } from 'src/app/classes/Card';



@Component({
  selector: 'app-deck-detail-user',
  templateUrl: './deck-detail-user.component.html',
  styleUrls: ['./deck-detail-user.component.css']
})
export class DeckDetailUserComponent implements OnInit {

  constructor() { }

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
  
arrayCards = new Array(this.card, this.card2, this.card3)
 
mainDeckCards = new Array(this.card)

extraDeckCards = new Array(this.card)

sideDeckCards = new Array()



  ngOnInit() {
     
    
      console.log(this.arrayCards, this.mainDeckCards)
      

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
      console.log(event.item.getRootElement())
    }
  }

}


