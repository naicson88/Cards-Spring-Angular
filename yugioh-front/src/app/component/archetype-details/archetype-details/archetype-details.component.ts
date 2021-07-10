import { AfterViewInit, Component, OnInit } from '@angular/core';
import { Archetype } from 'src/app/module/Archetype';
import { AchetypeService } from 'src/app/service/archetype-service/achetype.service';

import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { ImageToolTip } from 'src/app/Util/ImageToolTip';

@Component({
  selector: 'app-archetype-details',
  templateUrl: './archetype-details.component.html',
  styleUrls: ['./archetype-details.component.css']
})
export class ArchetypeDetailsComponent  implements OnInit  {
  archetype: Archetype[] = [];

  constructor(private archService: AchetypeService, private tool: ImageToolTip) {
   
  }
  

  ngOnInit() {
    this.loadDeckDetails();
  }

  storedCardId(event){
    const id = event.target.name;
    console.log(id);
    localStorage.setItem("idCard", id);
   
  }

  cardImagem(cardId: any){
    let urlimg = 'https://storage.googleapis.com/ygoprodeck.com/pics/' + cardId + '.jpg';
    return urlimg;
  }

  loadDeckDetails(){

    const id = localStorage.getItem("idArchetype");
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
