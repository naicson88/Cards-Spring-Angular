import { stringify } from '@angular/compiler/src/util';
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Imagens } from 'src/app/classes/Imagens';
import { CardServiceService } from 'src/app/service/card-service/card-service.service';

@Component({
  selector: 'app-usercards',
  templateUrl: './usercards.component.html',
  styleUrls: ['./usercards.component.css']
})
export class UsercardsComponent implements OnInit {
  @ViewChild('btnNew',  { static: false }) btnNew: ElementRef;

  constructor(private img: Imagens, private service: CardServiceService) { }

  iconsMap:any;
  arrIcons = new Array();

  ngOnInit() {
    this.map();
  }

  map(){
    let iconsMap = this.img.mapCardsIcons();  
    iconsMap.forEach((img:string, value: string ) =>{
      let obj =  {'tipo': value, 'img': img}
      this.arrIcons.push(obj);
    },);
    console.log(this.arrIcons);
  }

  listCards(event:any) {
    let type = event.target.name;

    if(type != null && type != undefined && type != ""){
        
    }

  }

  openSide(){
    let sideBar = (<HTMLInputElement>document.getElementById("mySidebar"));
    sideBar.style.width = "300px";
  }
  
}


