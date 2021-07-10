import { Component, Inject, OnInit ,Pipe, PipeTransform} from '@angular/core';
import { Deck } from 'src/app/module/deck';
import { DeckService } from 'src/app/service/deck.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { SpinnerService } from 'src/app/service/spinner.service';


@Component({
  selector: 'app-deck',
  templateUrl: './deck.component.html',
  styleUrls: ['./deck.component.css']
})

@Pipe({
  name: 'appFilter'
})

export class DeckComponent implements OnInit {
 page: number = 1; 
 pageSize: number = 8
 pageSizes = [8,12,24,36,100];
 totalItens = 0;

 deck: Deck[]
 relUserDeck: any[];
 safeUrl: SafeUrl;
 deckFilter: any= {nome:''}

 user: any;
 
  constructor(private service: DeckService, private domSanitizer: DomSanitizer, private  router: Router,
     private toastr: ToastrService, private  spinner: SpinnerService) { }

  ngOnInit() {
    this.getDecksInfo();
  }

  deckDetailPage(nome:any){
    if(nome != null ){
      this.router.navigate(['deck-details', nome]);
    }
  }

  
  getDecksInfo(): void {
  
    const params = this.getRequestParam(this.pageSize, this.page);
    

    this.service.getDecks(params).subscribe(data => {
     const {content, totalElements} = data;
      //this.deck = data
      this.deck = content;
      this.totalItens = totalElements;

      for(let i = 0; i < this.deck.length; i++){
        //Angular apresentava como se o link da imagem fosse unsafe/perigoso
        this.safeUrl = this.domSanitizer.bypassSecurityTrustUrl(this.deck[i].imagem);  

      }
      let decksIds = [];

      for(var i = 0; i < this.deck.length; i++){
        if(this.deck[i]['id'] != null){decksIds.push(this.deck[i]['id'] )}
       }

       console.log(this.deck);

      this.service.relUserDeck(decksIds).subscribe(rel => {
       this.relUserDeck = rel;
       console.log(this.relUserDeck);
      //Adiciona ao objeto a quantidade de Decks que o usuário tem
       this.deck.forEach( comp => {
         this.relUserDeck.map( e => {
           if(e.deckId === comp.id){
             Object.assign(comp, {"quantityUserHave": e.quantity})
           }
         })
       })

       console.log(this.deck)

     })

     
    })

    error => {
      console.log(error);
    }
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

  manegeDeckAndCardsOfUserCollection(event:any, flagAddOrRemove:string){
    let qtdCardManeged:number;
    let deckId = event.target.name;

    this.service.manegeDeckAndCardsOfUserCollection(deckId, flagAddOrRemove).subscribe(data => {
      qtdCardManeged = data;

      if(qtdCardManeged == 0){
        return false;
      }

      if(flagAddOrRemove === "A"){
      
        if(qtdCardManeged > 0){
          this.toastr.success('The Deck has been added to your collection! Plus ' + qtdCardManeged + ' cards of this Deck.', 'Success!');
            
          this.manegeQuantity(deckId, flagAddOrRemove);

        } else {
          this.toastr.error('Unable to add the Deck or Cards to the user.', 'Error!')
        }
      } else if( flagAddOrRemove === "R"){
        
        if(qtdCardManeged > 0){

          this.toastr.warning('The Deck has been removed from your collection! Plus ' + qtdCardManeged + ' cards of this deck.', 'Success!');
          this.manegeQuantity(deckId, flagAddOrRemove);

        } else {
          this.toastr.error('Unable to remove the Deck or Cards to the user collection.', 'Error!')
        }
      }

    })

  }

  manegeQuantity(deckId:string, flagAddOrRemove:string){
  
    let id = "inp_"+deckId;
    let el =(<HTMLInputElement>document.getElementById(id));
    let valor = el.value;
    if(valor != null){
      let parsed = parseInt(valor);
      
      if(flagAddOrRemove == 'A'){
        parsed += 1;
      } else if (flagAddOrRemove == 'R' && parsed > 0){
        parsed -= 1;
      } else {
        
        return ;
      }      
      
      if(parsed != NaN && parsed != undefined){
        (<HTMLInputElement>document.getElementById(id)).value = parsed.toString();
      
      }    
    }  
  }

  getRelUserDecks(decks: Deck[]){

  }

  ordenacaoArrayAPI(){
    this.deck.sort(function(a,b){
      return a.id < b.id ? -1 : a.id > b.id ? 1 : 0;  
    })
  }

  handlePageSizeChange(event): void {

    this.pageSize = event.value;
    this.page = 1;
    this.getDecksInfo();
  }

  handlePageChange(event) {
      this.page = event;
      this.getDecksInfo();
   
  }

  storeDeckId(event){
    const id = event.target.name;
    localStorage.setItem("idDeckDetails", id);
    console.log(id);
  }

  addDeckToCollection(e){
    const deckId = e.target.name;
    
    console.log(deckId, this.user)
   this.toastr.success('The Deck has been added to your collection!', 'Success!')
  }
}
