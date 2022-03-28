import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Card } from 'src/app/classes/Card';
import { AchetypeService } from 'src/app/service/archetype-service/achetype.service';
import { CardServiceService } from 'src/app/service/card-service/card-service.service';
import { GeneralFunctions } from 'src/app/Util/GeneralFunctions';
import {Chart} from   'Chart.js';

@Component({
  selector: 'app-card-detail',
  templateUrl: './card-detail.component.html',
  styleUrls: ['./card-detail.component.css']
})
export class CardDetailComponent implements OnInit {
  @ViewChild("attrCanvas",{static: true}) elemento: ElementRef;
  raridade:string;

  

  constructor(private router: Router, private service: CardServiceService, private archService: AchetypeService) { }


  ngOnInit() {
    window.scrollTo(0, 0);
    this.loadCardDetail();
    this.cardPriceGrafic();

  }

  card: Card[]=[];
  userKonamiCollectionMap: Map<any,any>
  userHaveByUserCollection: Map<any,any>;
  totalViews:number;

  loadCardDetail(){
   // const id = localStorage.getItem("idCard");
    let id = this.service.getCardNumber();
    
    if(id == null || id == undefined){
      id = Number(localStorage.getItem("idCard"));
    }
      this.service.getCardDetails(id).subscribe(data => { 
        this.card = data['card'];
        this.qtdUserHaveByKonamiCollection(data);
        this.qtdUserHaveByUserCollection(data);
        this.totalViews = data['views']['totalQtdViews'];

      })  
  
  }

  cardImagem(cardId: any){
   // let urlimg = 'https://storage.googleapis.com/ygoprodeck.com/pics/' + cardId + '.jpg';
      let urlimg = GeneralFunctions.cardImagem + cardId + '.jpg';
      return urlimg;
  }

  storeDeckId(event){
    const id = event.target.name;
    localStorage.setItem("idDeckDetails", id);
    
  }

  atributoImagem(atributo:string){
    switch(atributo){
      case 'WATER':
      return '..\\..\\assets\\img\\outras\\WATER.png';
      case 'EARTH':
        return '..\\..\\assets\\img\\outras\\TERRA.png';
      case 'FIRE':
        return '..\\..\\assets\\img\\outras\\FIRE.png';
      case 'LIGHT':
        return '..\\..\\assets\\img\\outras\\LUZ.png';
      case 'DARK':
        return '..\\..\\assets\\img\\outras\\DARK.png';
      case 'WIND':
        return '..\\..\\assets\\img\\outras\\WIND.png';
        case 'Spell Card':
          return '..\\..\\assets\\img\\outras\\MAGIA.png';
        case 'Trap Card':
          return '..\\..\\assets\\img\\outras\\ARMADILHA.png';
        case 'Continuous':
          return '..\\..\\assets\\img\\outras\\Continuous.png';
        case 'Field':
          return '..\\..\\assets\\img\\outras\\Field.png';
        case 'Quick-Play':
            return '..\\..\\assets\\img\\outras\\Quick.png';
        case 'Counter':
          return '..\\..\\assets\\img\\outras\\Counter.png';
        case 'Equip':
          return '..\\..\\assets\\img\\outras\\Equip.jpg'; 
    }
    
  }

  /*
  tipoImagem(tipo:string){
    switch(tipo){
      case 'Aqua': return '..\\..\\assets\\img\\tiposMonstros\\Aqua.png';
      case 'Beast': return '..\\..\\assets\\img\\tiposMonstros\\Beast-DG.png';
      case 'Beast-Warrior': return '..\\..\\assets\\img\\tiposMonstros\\Beast-Warrior-DG.png';
    //  case 'Creator-God'  : return '..\\..\\assets\\img\\tiposMonstros\\Beast-Warrior-DG.png';
    case 'Cyberse' : return '..\\..\\assets\\img\\tiposMonstros\\Cyberse.PNG';
    case 'Dinosaur' : return '..\\..\\assets\\img\\tiposMonstros\\Dinosaur-DG.png';
    case 'Divine-Beast' : return '..\\..\\assets\\img\\tiposMonstros\\Divine-Beast-DG.png';
    case 'Dragon' : return '..\\..\\assets\\img\\tiposMonstros\\Dragon-DG.png';
    case 'Fairy': return '..\\..\\assets\\img\\tiposMonstros\\Fairy-DG.png';
    case 'Fiend' : return '..\\..\\assets\\img\\tiposMonstros\\Fiend-DG.png';
    case 'Fish' : return '..\\..\\assets\\img\\tiposMonstros\\Fish-DG.png';
    case 'Insect' : return '..\\..\\assets\\img\\tiposMonstros\\Insect-DG.png';
    case 'Machine' : return '..\\..\\assets\\img\\tiposMonstros\\Machine-DG.png';
    case 'Plant' : return '..\\..\\assets\\img\\tiposMonstros\\Plant-DG.png';
    case 'Psychic' : return '..\\..\\assets\\img\\tiposMonstros\\Psychic-DG.png';
    case 'Pyro' : return '..\\..\\assets\\img\\tiposMonstros\\Pyro-DG.png';
    case 'Reptile': return '..\\..\\assets\\img\\tiposMonstros\\Reptile-DG.png';
    case 'Rock': return '..\\..\\assets\\img\\tiposMonstros\\Rock-DG.png';
    case 'Sea Serpent': return '..\\..\\assets\\img\\tiposMonstros\\Sea_Serpent-DG.png';
    case 'Spellcaster': return '..\\..\\assets\\img\\tiposMonstros\\Spellcaster-DG.png';
    case 'Thunder': return '..\\..\\assets\\img\\tiposMonstros\\Thunder-DG.png';
    case 'Warrior': return '..\\..\\assets\\img\\tiposMonstros\\Warrior-DG.png';
    case 'Winged Beast' : return '..\\..\\assets\\img\\tiposMonstros\\Winged_Beast-DG.png';
    case 'Wyrm' : return '..\\..\\assets\\img\\tiposMonstros\\Wyrm-DG.png';
    case 'Zombie': return '..\\..\\assets\\img\\tiposMonstros\\Zombie-DG.png';
    }
  } */

  corRaridade(raridade:string){  
    if(raridade == 'Common'){
      this.raridade = "C";
      return "comum";
    }
    if(raridade == 'Rare'){
      this.raridade = "R";
      return "raro";
    }
    if(raridade == 'Super Rare'){
      this.raridade = "SR"
      return "super-raro";
    }
    if(raridade == 'Ultra Rare'){
      this.raridade = "UR";
      return "ultra-raro";
    }

  }

  
  isShowTooltip: boolean = false;
  imgTooltip: string;
  topTp;
  leftTp;

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

  storedArchetype(event){
    //const id = event.target.id;
    const archId = event.target.id;
   localStorage.setItem("idArchetype", archId);
   // console.log(id);s
   if(archId != null && archId != ""){  
     console.log( "archid" + archId)
     this.archService.setArchetypeId(archId);

   } else {
      console.log("Unable to consult this card, try again later.");
      return false;
   }
  }

  cardPriceGrafic(){


  const data = {
    labels: ['5 Weeks ago', '4 Weeks ago', '3 Weeks ago', '2 Weeks ago', 'Current'],
    datasets: [
      {
        label: 'Dataset 1',
        data: [12.30,21,23,25,30,10],
        borderColor: 'rgba(255, 99, 132, 1)',
        backgroundColor: 'rgba(255, 0, 0, 0)',
      },
      {
        label: 'Dataset 2',
        data: [19,30,10,25,31,11],
        borderColor: 'rgba(54, 162, 235, 1)',
        backgroundColor: 'rgba(255, 0, 0, 0)',
      }
    ]
  };

    new Chart(this.elemento.nativeElement, {
      type: 'line',
      data: data,
      options: {
          scales: {
              y: {
                  beginAtZero: true
              }
          },
          plugins:{
            legend: {
              labels: {
                usePointStyle: true
              }
            }
          },
          responsive: true

      }
    });
  }

   qtdUserHaveByKonamiCollection(data:any) {
     // console.log(JSON.stringify(data['qtdUserHaveByKonamiCollection']));
    let  result = Object.entries(data['qtdUserHaveByKonamiCollection']);
    this.userKonamiCollectionMap = new Map(result);
   // console.log(this.userKonamiCollectionMap);
   }

   qtdUserHaveByUserCollection(data:any){
     let result = Object.entries(data['qtdUserHaveByUserCollection']);
     this.userHaveByUserCollection = new Map(result);
     console.log(this.userHaveByUserCollection)
   }

}
