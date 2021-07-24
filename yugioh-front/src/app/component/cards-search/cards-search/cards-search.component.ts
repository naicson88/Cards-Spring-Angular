import { COMPILER_OPTIONS, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Imagens } from 'src/app/module/Imagens';
import { Cards } from 'src/app/module/Rel_Deck_Cards';
import { SearchCriteria } from 'src/app/module/SearchCriteria';
import { CardServiceService } from 'src/app/service/card-service/card-service.service';

@Component({
  selector: 'app-cards-search',
  templateUrl: './cards-search.component.html',
  styleUrls: ['./cards-search.component.css']
})
export class CardsSearchComponent implements OnInit {

  constructor(private imagens: Imagens, private cardService: CardServiceService) { }

  ngOnInit() {
   
  }
  panelOpenState = false;
  cardsFound: Cards[] = [];

  //Cards input field data 
 cardname = '';
 number = '';
 level = '';
 plusatk = '';
 lessatk = '';
 plusdef = '';
 lessdef = '';
 description = '';
 links = '';
 pendulum = '';   	

  criterios =  new Array();
  atributos = [
    {name: this.imagens.dark, img: this.imagens.dark_img},
    {name: this.imagens.fire, img: this.imagens.fire_img},
    {name: this.imagens.wind, img: this.imagens.wind_img},
    {name: this.imagens.light, img: this.imagens.light_img},
    {name: this.imagens.earth, img: this.imagens.earth_img},
    {name: this.imagens.water, img: this.imagens.water_img},
    {name: this.imagens.continuous, img: this.imagens.continuous_img},
    {name: this.imagens.field, img: this.imagens.field_img},
    {name: this.imagens.counter, img: this.imagens.counter_img},
    {name: this.imagens.equip, img: this.imagens.equip_img}

  ]

  types = [

    {name: this.imagens.aqua, img: this.imagens.aqua_img},
    {name: this.imagens.beast, img: this.imagens.beast_img},
    {name: this.imagens.beast_warrior, img: this.imagens.beast_warrior_img},
    {name: this.imagens.cyberse, img: this.imagens.cyberse_img},
    {name: this.imagens.dinosaur, img: this.imagens.dinosaur_img},
    {name: this.imagens.divine_beast, img: this.imagens.divine_beast_img},
    {name: this.imagens.dragon, img: this.imagens.dragon_img},
    {name: this.imagens.fairy, img: this.imagens.fairy_img},
    {name: this.imagens.fiend, img: this.imagens.fiend_img},
    {name: this.imagens.fish, img: this.imagens.fish_img},
    {name: this.imagens.insect, img: this.imagens.insect_img},
    {name: this.imagens.machine, img: this.imagens.machine_img},
    {name: this.imagens.plant, img: this.imagens.plant_img},
    {name: this.imagens.pyro, img: this.imagens.pyro_img},

    {name: this.imagens.reptile, img: this.imagens.reptile_img},
    {name: this.imagens.rock, img: this.imagens.rock_img},
    {name: this.imagens.sea_serpent, img: this.imagens.sea_serpent_img},
    {name: this.imagens.spellcaster, img: this.imagens.spellcaster_img},
    {name: this.imagens.thunder, img: this.imagens.thunder_img},
    {name: this.imagens.warrior, img: this.imagens.warrior_img},
    {name: this.imagens.winged_beast, img: this.imagens.winged_beast_img},
    {name: this.imagens.wyrm, img: this.imagens.wyrm_img},
    {name: this.imagens.zombie, img: this.imagens.zombie_img},

  ]

  cards =[
    {name: this.imagens.monster, img: this.imagens.monster_img},
    {name: this.imagens.spellc, img: this.imagens.spellc_img},
    {name: this.imagens.trapc, img: this.imagens.trapc_img},
    {name: this.imagens.pendulum, img: this.imagens.pendulum_img},
    {name: this.imagens.xyz, img: this.imagens.xyz_img},
    {name: this.imagens.synchron, img: this.imagens.synchron_img},
    {name: this.imagens.fusion, img: this.imagens.fusion_img},
    {name: this.imagens.link, img: this.imagens.link_img},
    {name: this.imagens.ritual, img: this.imagens.ritual_img},
  ]

  searchCards(){

    this.inputFilters();

    if(this.criterios != null && this.criterios.length > 0){
      
      this.cardService.searchCards(this.criterios).subscribe(data => {
        this.cardsFound = data;
        console.log(this.cardsFound);
      this.cardsFound = [];
      }) 

     
    }
    console.log(this.criterios);
    
  }

  inputFilters(){

    if(this.cardname != null && this.cardname != ''){
        const criterio = new SearchCriteria();
        criterio.criterios('nome', 'MATCH', this.cardname );
        this.criterios.push(criterio);           
    }

      if(this.number != null && this.number != ''){
        const criterio = new SearchCriteria();
        criterio.criterios('numero', 'EQUAL', this.number );
        this.criterios.push(criterio);           
      }

      if(this.level != null && this.level != ''){
          const criterio = new SearchCriteria();
          criterio.criterios('nivel', 'EQUAL', this.number );
          this.criterios.push(criterio);           
        }

  }



}
