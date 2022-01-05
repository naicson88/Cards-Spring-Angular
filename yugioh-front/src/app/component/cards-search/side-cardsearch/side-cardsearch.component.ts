import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Imagens } from 'src/app/classes/Imagens';
import { GeneralFunctions } from 'src/app/Util/GeneralFunctions';
import { SideSearchcardService } from './side-searchcard.service';

@Component({
  selector: 'app-side-cardsearch',
  templateUrl: './side-cardsearch.component.html',
  styleUrls: ['./side-cardsearch.component.css']
})
export class SideCardsearchComponent implements OnInit {
  searchForm: FormGroup;
  constructor(private img: Imagens, private service: SideSearchcardService, private formBuilder: FormBuilder) { 
    this.createForm();
  }

  back_img: string = this.img.back_img;
  cardFound:any [] = [];
  arrSets: Object;
  arrUserSets: Object;

  ngOnInit() {
  }

  createForm(){
    
    this.searchForm = this.formBuilder.group({
      cardName: ['', Validators.required],
    });
      
  }

  findCardToAddToUserCollection() {
      let teste = 72302403;
      let name = this.searchForm.controls['cardName'].value
      console.log(name)
      this.service.findCardToAddToUserCollection(teste).subscribe(data  => {
        this.cardFound = data;
        this.arrSets = data.mapDeckSetcode;
        this.arrUserSets = data.mapSetsOfUser;
       
      })
  }

   closeNav() {
    let sideBar = (<HTMLInputElement>document.getElementById("mySidebar"));
    sideBar.style.width = "0";
   
  }
  
  cardImagem(cardId: any){
    // let urlimg = 'https://storage.googleapis.com/ygoprodeck.com/pics/' + cardId + '.jpg';
       let urlimg = GeneralFunctions.cardImagem + cardId + '.jpg';
       return urlimg;
   }
  
}
