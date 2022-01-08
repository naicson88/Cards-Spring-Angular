import { Component, ElementRef, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { EventEmitter } from 'protractor';
import { Card } from 'src/app/classes/Card';
import { Imagens } from 'src/app/classes/Imagens';
import { SearchCriteria } from 'src/app/classes/SearchCriteria';
import { CardServiceService } from 'src/app/service/card-service/card-service.service';
import { GeneralFunctions } from 'src/app/Util/GeneralFunctions';

@Component({
  selector: 'app-search-box',
  templateUrl: './search-box.component.html',
  styleUrls: ['./search-box.component.css']
})
export class SearchBoxComponent implements OnInit {


  constructor(private imagens: Imagens, private cardService: CardServiceService, {nativeElement}: ElementRef<HTMLImageElement>, @Inject(MAT_DIALOG_DATA) public data: Card[],
  @Inject(MAT_DIALOG_DATA) public crietrias: any[],
  private dialogRef: MatDialogRef<SearchBoxComponent>) {
    const supports = 'loading' in HTMLImageElement.prototype;

    if(supports){
      nativeElement.setAttribute('loading','lazy');
    }
   }
 
  ngOnInit() {
    window.scrollTo(0, 0);
    
  }

  loading: boolean = true
  onLoad() {
      this.loading = false;

  }
  //Tooltip image
  topTp;
  leftTp;
  imgTooltip: string;
  isShowTooltip: boolean = false;
  isRandomCards : boolean = true;
  cardsFoundQtd: number;


  panelOpenState = false;
  chosen:string;
  cardsFound: Card[] = [];
  relUserCard: any;

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

  sorts: Object[] = [
    {value: 'name', viewValue: 'Name'},
    {value: 'atk', viewValue: 'Attack'},
    {value: 'def', viewValue: 'Defense'},
    {value: 'level', viewValue: 'Level'},
    {value: 'links', viewValue: 'Links'},
    {value: 'pend', viewValue: 'Pendulum Scale'},
  ];

  searchCards(){

    this.criterios = []

    this.inputFilters();
    
    this.cardsFilters();

    this.attrFilters();
    
    this.typesFilters();

    if(this.criterios != null && this.criterios.length > 0){

      const params = this.getRequestParam(this.pageSize, this.page)

      this.cardService.searchCardsDetailed(params, this.criterios).subscribe(data => {
        this.cardsFound = data;
        console.log(this.cardsFound);

       this.relUserCard = GeneralFunctions.relUserCards(this.cardsFound, this.cardService);
       
       this.dialogRef.close({data: this.cardsFound, criterias: this.criterios})
  


       /* for(var i = 0; i < this.cardsFound.length; i++){
          if(this.cardsFound[i]['numero'] != null){cardNumbers.push(this.cardsFound[i]['numero'] )}
         }

        this.cardService.relUserCards(cardNumbers).subscribe(rel =>{
          this.relUserCard = rel;
         
          this.cardsFound.forEach( comp => {
            this.relUserCard.map( e => {
              if(e.cardNumero === comp.numero){
                Object.assign(comp, {"qtd": e.qtd})
              }
            })
          })
        });*/

      }) 
  
    }
    //console.log(this.criterios);
    
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

          if(this.plusatk != null && this.plusatk != '' && parseInt(this.plusatk) >= 0){
              const criterio = new SearchCriteria();  
              criterio.criterios('atk', 'GREATER_THAN_EQUAL', this.plusatk );
              this.criterios.push(criterio);
          }

          if(this.lessatk != null && this.lessatk != '' && parseInt(this.lessatk) >= 0){
            const criterio = new SearchCriteria();
            criterio.criterios('atk', 'LESS_THAN_EQUAL', this.lessatk );
            this.criterios.push(criterio);
        }

          if(this.plusdef != null && this.plusdef != '' && parseInt(this.plusdef) >= 0){
            const criterio = new SearchCriteria();  
            criterio.criterios('def', 'GREATER_THAN_EQUAL', this.plusdef );
            this.criterios.push(criterio);
        }

          if(this.lessdef != null && this.lessdef != '' && parseInt(this.lessdef) >= 0){
            const criterio = new SearchCriteria();  
            criterio.criterios('def', 'LESS_THAN_EQUAL', this.lessdef );
            this.criterios.push(criterio);
        } 

          if(this.description != null && this.description != ''){
            const criterio = new SearchCriteria();  
            criterio.criterios('descricao', 'MATCH', this.description );
            this.criterios.push(criterio);
        }

          // Inputs que possam ter Between
          if(this.level != null && this.level != ''){
        
            if(this.level.indexOf("-") !== -1){
                this.splitString(this.level, 'nivel');

            } else {
              const criterio = new SearchCriteria();
              criterio.criterios('nivel', 'EQUAL', this.level );
              this.criterios.push(criterio);    
            }

          }

          if(this.links != null && this.links != ''){
          

            if(this.links.indexOf("-") !== -1){
                this.splitString(this.links, 'qtd_link');

            } else {
              const criterio = new SearchCriteria();
              criterio.criterios('qtd_link', 'EQUAL', this.links );
              this.criterios.push(criterio);    
            }

          }

          if(this.pendulum != null && this.pendulum != ''){
        
            if(this.pendulum.indexOf("-") !== -1){
                this.splitString(this.pendulum, 'escala');

            } else {
              const criterio = new SearchCriteria();
              criterio.criterios('escala', 'EQUAL', this.pendulum );
              this.criterios.push(criterio);    
            }

          }

      }

      cardsFilters(){

            const cards = document.querySelectorAll('.cards');
            const criterio = new SearchCriteria();
            let param = new Array();

            if(cards != null && cards.length > 0){

              for(var i = 0; i < cards.length; i++){
                if(cards[i].className.indexOf("mat-checkbox-checked") !== -1)
                param.push(cards[i].getElementsByTagName('input')[0].defaultValue)
              }

              if(param != null && param.length > 0){
                criterio.criterios('genericType', 'IN', param);
                this.criterios.push(criterio);
              }
              
            }

      }

      attrFilters(){
        const attrs = document.querySelectorAll('.attr');
        const criterio = new SearchCriteria();
        const criterio2 = new SearchCriteria();
        let arrAttr = new Array();
        let arrProp = new Array();

        if(attrs != null && attrs.length > 0){

          for(var i = 0; i < attrs.length; i++){
            if(attrs[i].className.indexOf("mat-checkbox-checked") !== -1){
              let txt = attrs[i].getElementsByTagName('input')[0].defaultValue;

              if(txt == 'Continuous' || txt == 'Field' || txt == 'Counter' || txt == 'Equip'){
                  arrProp.push(txt);
              } else {
                arrAttr.push(txt);
              }
            }
                     
          }

          if(arrProp != null && arrProp.length > 0){
            criterio.criterios('propriedade', 'IN', arrProp);
            this.criterios.push(criterio);
          }

          if(arrAttr != null && arrAttr.length > 0){
            criterio2.criterios('atributo', 'IN', arrAttr);
            this.criterios.push(criterio2);
          }

        }
      
       console.log(this.criterios);
      }

      typesFilters(){

        const types = document.querySelectorAll('.types');
        const criterio = new SearchCriteria();
        let param = new Array();

        if(types != null && types.length > 0){

          for(var i = 0; i < types.length; i++){
            if(types[i].className.indexOf("mat-checkbox-checked") !== -1)
            param.push(types[i].getElementsByTagName('input')[0].defaultValue)
          }

          if(param != null && param.length > 0){
            criterio.criterios('tipos', 'IN', param);
            this.criterios.push(criterio);
          }
          
        }

          console.log(this.criterios);
      }
      
      splitString(txt:string, key:string){
          console.log(key)
            const criterio = new SearchCriteria();
            const criterio2 = new SearchCriteria();

            const splitted = txt.split("-",2);

            criterio.criterios(key, 'GREATER_THAN_EQUAL', splitted[0] );
            this.criterios.push(criterio);

            criterio2.criterios(key, 'LESS_THAN_EQUAL', splitted[1] );
            this.criterios.push(criterio2);

      }

      cardImagem(cardId: any){
        let urlimg = 'https://storage.googleapis.com/ygoprodeck.com/pics/' + cardId + '.jpg';
        return urlimg;
      }

      mostrarImgToolTip(img:string, e){
          this.leftTp =  e.pageX + 15 + "px";
          this.topTp = + e.pageY + 15 + "px";
    
          //this.imgTooltip = img;
          this.imgTooltip = e.target.src;
          this.isShowTooltip = true;
      }
   
      esconderImgToolTip(){
        this.isShowTooltip = false;
      }

      page: number = 1; 
      pageSize: number = 30;
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


}
