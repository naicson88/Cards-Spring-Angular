import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { Deck } from 'src/app/classes/deck';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css']
})
export class AdminDashboardComponent implements OnInit {

  formDeck: FormGroup;

  constructor() { }

  ngOnInit() {
    this.createFormDeck(new Deck())
  }
  onSubmit(){
    console.log(this.formDeck.value)
  }

  createFormDeck(deck:Deck){
    this.formDeck = new FormGroup({
      name: new FormControl(deck.nome),
      namePortuguese: new FormControl(deck.nomePortugues),
      setType: new FormControl(deck.setType),
      releaseDate: new FormControl(deck.lancamento)
    })
  }

}
