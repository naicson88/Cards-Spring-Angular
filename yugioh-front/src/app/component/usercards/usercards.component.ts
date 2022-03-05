
import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Imagens } from 'src/app/classes/Imagens';
import { CardServiceService } from 'src/app/service/card-service/card-service.service';
import { GeneralFunctions } from 'src/app/Util/GeneralFunctions';
import * as _ from 'lodash'
import { BehaviorSubject } from 'rxjs';
import { MatDialog } from '@angular/material';
import { ErrorDialogComponent } from '../dialogs/error-dialog/error-dialog.component';
import { WarningDialogComponent } from '../dialogs/warning-dialog/warning-dialog.component';
import { CardinfoComponent } from '../tooltip/cardinfo/cardinfo.component';
import { Card } from 'src/app/classes/Card';


@Component({
  selector: 'app-usercards',
  templateUrl: './usercards.component.html',
  styleUrls: ['./usercards.component.css']
})
export class UsercardsComponent implements OnInit {
  @ViewChild('btnNew',  { static: false }) btnNew: ElementRef;

  constructor(private img: Imagens, private service: CardServiceService, private dialog: MatDialog, private tooltip: CardinfoComponent) { }

  cardsFromScroll = new BehaviorSubject([]);
  page: number = 1; 
  pageSize: number = 20;

  iconsMap:any;
  arrIcons = new Array();
  arrCards = new Array();
  arrCardsFromScroll = new Array();
  
  arrCardsDetails:[] = [];

  genericTypeAtual: string = 'MONSTER'

  cardname = '';

  //Serão enviadas para o tooltip
  cardImage:string;
  card:Card;

  ngOnInit() {
    this.map();
    this.cardsByGenericType(this.genericTypeAtual);
  }

  map(){
    let iconsMap = this.img.mapCardsIcons();  
    iconsMap.forEach((img:string, value: string ) =>{
      let obj =  {'tipo': value, 'img': img}
      this.arrIcons.push(obj);
    },);
    console.log(this.arrIcons);
  }

  openSide(){
    let sideBar = (<HTMLInputElement>document.getElementById("mySidebar"));
    sideBar.style.width = "300px";
  }
  
  cardsByGenericType(genericType:string){
    this.genericTypeAtual = genericType;
    const params = this.getRequestParam(this.pageSize, 0);

    if(genericType != null && genericType != " "){
      this.service.getCardsByGenericType(params, genericType).subscribe(data => {
        this.arrCards = data;
        this.page = 2;
      })
    }
  }

  cardImagem(cardId: any){
    // let urlimg = 'https://storage.googleapis.com/ygoprodeck.com/pics/' + cardId + '.jpg';
       let urlimg = GeneralFunctions.cardImagem + cardId + '.jpg';
       return urlimg;
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
      const params = this.getRequestParam(this.pageSize, this.page);
    
      this.service.getCardsByGenericType(params, this.genericTypeAtual).subscribe(newCards => {
        this.arrCardsFromScroll = newCards;

        this.arrCardsFromScroll.forEach(card => {
          this.arrCards.push(card);
        });

        this.page = this.page + 1;
      })
    
    }

    isShowTooltip: boolean = false;
    imgTooltip: string;
    topTp;
    leftTp;

    mostrarDivCardsInfo(e, cardNumber:any){

      this.leftTp =  e.pageX + 15 + "px";
      this.topTp = + e.pageY + 15 + "px";
      this.isShowTooltip = true;
     
      this.cardImage = GeneralFunctions.cardImagem + cardNumber + '.jpg';
      this.service.findByNumero(cardNumber).subscribe(card => { console.log(this.card); this.card = card  });
    
    }
 
    mostrarImgToolTip(e){
        this.leftTp =  e.pageX + 15 + "px";
        this.topTp = + e.pageY + 15 + "px";
   
        //this.imgTooltip = img; se necessario coloca mais um argumento, o caminho da imagem
        this.imgTooltip = e.target.src;
        this.isShowTooltip = true;
     }
   
     esconderImgToolTip(){
      this.isShowTooltip = false;
    }
    
    qtdTotal:number = 0;
    
    cardOfUserDetails(cardNumber) {

        if(cardNumber == null || cardNumber == undefined)
            alert("It was not possible consult, Try again later.")

        this.service.cardOfUserDetails(cardNumber).subscribe(data =>{
          let qtd = 0;
          this.arrCardsDetails = data;
 
          this.arrCardsDetails['setsWithThisCard'].forEach(element => {
            qtd += element.quantity 
          });

          this.setQtdRarity();

          this.qtdTotal = qtd;
          console.log(this.arrCardsDetails)
        
        })
    }

    qtdCommon: number = 0;
    qtdRare: number = 0;
    qtdSuperRare:number = 0;
    qtdUltraRare: number = 0;

    setQtdRarity(){
       if(this.arrCardsDetails['rarity']['Common'] != null && this.arrCardsDetails['rarity']['Common'] != undefined)
           this.qtdCommon = this.arrCardsDetails['rarity']['Common'];

       if(this.arrCardsDetails['rarity']['Rare'] != null && this.arrCardsDetails['rarity']['Rare'] != undefined)
          this.qtdRare = this.arrCardsDetails['rarity']['Rare'];

        if(this.arrCardsDetails['rarity']['Ultra Rare'] != null && this.arrCardsDetails['rarity']['Ultra Rare'] != undefined)
          this.qtdUltraRare = this.arrCardsDetails['rarity']['Ultra Rare'];
        
          if(this.arrCardsDetails['rarity']['Super Rare'] != null && this.arrCardsDetails['rarity']['Super Rare'] != undefined)
          this.qtdSuperRare = this.arrCardsDetails['rarity']['Super Rare'];
      
    }

    searchCardsByName(){

        if(this.cardname != null && this.cardname != ""){
          this.service.searchCardsByName(this.cardname).subscribe(data=>{
         
            if(Object.keys(data).length > 0 )
              this.arrCards = data;
            else{
                this.errorDialog("No cards found with this name!")
            }
            
          })

        }else {
          this.warningDialog("Fill the field with a card name!")

          return false;
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



    storedCardId(event){
      /*  const id = event.target.name;
        console.log(id);
        localStorage.setItem("idCard", id);*/
    
        const cardNumber = event.target.name;
        if(cardNumber != null && cardNumber != ""){
          console.log(cardNumber)
          this.service.setCardNumber(cardNumber);
        
        } else {
           console.log("Unable to consult this card, try again later.");
           return false;
        }
       
      }
}


