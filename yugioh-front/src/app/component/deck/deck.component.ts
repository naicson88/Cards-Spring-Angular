import { Component, Inject, OnInit ,Pipe, PipeTransform} from '@angular/core';
import { Deck } from 'src/app/classes/deck';
import { DeckService } from 'src/app/service/deck.service';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
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
 pageSize: number = 12
 pageSizes = [12,24,48,100];
 totalItens = 0;

 set_type: any;

 deck: Deck[]
 relUserDeck: any[];
 safeUrl: SafeUrl;
 deckFilter: any= {nome:''}

 user: any;
 
  constructor(private service: DeckService, private domSanitizer: DomSanitizer, private  router: Router,
     private toastr: ToastrService, private  spinner: SpinnerService, private route: ActivatedRoute) { }

  ngOnInit() {

    this.route.data.subscribe(set_type =>{
      this.set_type = set_type.set_type;
    })

    this.getDecksInfo();

  }

  deckDetailPage(nome:any){
    if(nome != null ){
      this.router.navigate(['deck-details', nome]);
    }
  }

  
  getDecksInfo(): void {
  
    const params = this.getRequestParam(this.pageSize, this.page);
    
    this.service.getDecks(params, this.set_type).subscribe(data => {
  
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

    addSetToUserCollection(event:any){
      let qtdCardManeged:number;
      let setId = event.target.name;

      this.service.addSetToUsersCollection(setId).subscribe(data => {
        qtdCardManeged = data;

        if(qtdCardManeged == 0){
          return false;
        }

        if(qtdCardManeged > 0){
          this.toastr.success('The Deck has been added to your collection! Plus ' + qtdCardManeged + ' cards of this Deck.', 'Success!');
            
          this.manegeQuantity(setId, "A");

        } else {
          this.toastr.error('Unable to add the Deck or Cards to the user.', 'Error!')
        }

      })
    }

    removeSetToUserCollection(event:any) {
      
      let qtdCardManeged:number;
      let setId = event.target.id

      let conf= confirm("Are you sure you want to delete from your collection?")
      
      if(setId == null || setId == undefined || setId == "")
        alert("It was not possible remove this set. Try again later.")

      if(conf){

        this.service.removeSetToUsersCollection(setId).subscribe(data => {
          qtdCardManeged = data;
  
          if(qtdCardManeged > 0){
  
            this.toastr.warning('The Deck has been removed from your collection! Plus ' + qtdCardManeged + ' cards of this deck.', 'Success!');
            this.manegeQuantity(setId, "R");

          } else {
            this.toastr.error('Unable to remove the Deck or Cards to the user collection.', 'Error!')
          }
        })

      } else {
          return false;
      }
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

    this.pageSize = event//event.value;
    this.page = 1;
    this.getDecksInfo();
  }

  handlePageChange(event) {
      this.page = event;
      this.getDecksInfo();
   
  }

  storeDeckId(id:any){
  //  const id = event.target.name;
    localStorage.setItem("idDeckDetails", id);
    console.log(id);
  }

  addDeckToCollection(e){
    const deckId = e.target.name;
    
    //console.log(deckId, this.user)
   this.toastr.success('The Deck has been added to your collection!', 'Success!')
  }
}
