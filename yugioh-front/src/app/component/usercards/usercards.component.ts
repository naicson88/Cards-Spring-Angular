import { stringify } from '@angular/compiler/src/util';
import { Component, OnInit } from '@angular/core';
import { Imagens } from 'src/app/classes/Imagens';

@Component({
  selector: 'app-usercards',
  templateUrl: './usercards.component.html',
  styleUrls: ['./usercards.component.css']
})
export class UsercardsComponent implements OnInit {

  constructor(private img: Imagens) { }

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

  
}
function obj(obj: any): any {
  throw new Error('Function not implemented.');
}

