import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Card } from 'src/app/module/Card';
import { CardServiceService } from 'src/app/service/card-service/card-service.service';

@Component({
  selector: 'app-card-detail',
  templateUrl: './card-detail.component.html',
  styleUrls: ['./card-detail.component.css']
})
export class CardDetailComponent implements OnInit {

  raridade:string;
  

  constructor(private router: Router, private service: CardServiceService) { }

  ngOnInit() {
    this.loadCardDetail();
  }

  card: Card[]=[];

  loadCardDetail(){
    const id = localStorage.getItem("idCard");

    this.service.getCardDetails(id).subscribe(data => {
      this.card = data;
      console.log(this.card);
      console.log("chamou");
    })
  }

  cardImagem(cardId: any){
    let urlimg = 'https://storage.googleapis.com/ygoprodeck.com/pics/' + cardId + '.jpg';
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
  }

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
