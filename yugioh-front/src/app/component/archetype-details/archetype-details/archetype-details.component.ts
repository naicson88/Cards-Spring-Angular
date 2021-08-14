import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Archetype } from 'src/app/classes/Archetype';
import { AchetypeService } from 'src/app/service/archetype-service/achetype.service';

import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { ImageToolTip } from 'src/app/Util/ImageToolTip';
import { CardServiceService } from 'src/app/service/card-service/card-service.service';

@Component({
  selector: 'app-archetype-details',
  templateUrl: './archetype-details.component.html',
  styleUrls: ['./archetype-details.component.css']
})
export class ArchetypeDetailsComponent  implements OnInit  {
  archetype: Archetype[] = [];

  constructor(private archService: AchetypeService, private tool: ImageToolTip, private cardService: CardServiceService) {
   
  }
  

  ngOnInit() {
    this.loadDeckDetails();
  }

  storedCardId(event){
  /*  const id = event.target.name;
    console.log(id);
    localStorage.setItem("idCard", id);*/

    const cardNumber = event.target.name;
    if(cardNumber != null && cardNumber != ""){
      console.log(cardNumber)
      this.cardService.setCardNumber(cardNumber);
    
    } else {
       console.log("Unable to consult this card, try again later.");
       return false;
    }
   
  }

  cardImagem(cardId: any){
    let urlimg = 'https://storage.googleapis.com/ygoprodeck.com/pics/' + cardId + '.jpg';
    return urlimg;
  }

  loadDeckDetails(){

    //const id = localStorage.getItem("idArchetype");
    const id = this.archService.getArchetypeId();
    this.archService.getArchetype(id).subscribe(data =>{
      console.log(data)
     this.archetype = data;
     
    })
  }
   
   mostraImagem(e){
     console.log(e);
     this.tool.mostrarImgToolTip(e);
   }

   isShowTooltip: boolean = false;
    imgTooltip: string;
    topTp;
    leftTp;
 
    mostrarImgToolTip(e){
        console.log("e 2")
        this.leftTp =  e.pageX + 15 + "px";
        this.topTp = + e.pageY + 15 + "px";
   
        //this.imgTooltip = img; se necessario coloca mais um argumento, o caminho da imagem
        this.imgTooltip = e.target.src;
        this.isShowTooltip = true;
     }
   
     esconderImgToolTip(){
      this.isShowTooltip = false;
    }

}
