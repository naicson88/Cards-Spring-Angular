import { Component, Injectable, Input, OnInit } from '@angular/core';
import { Card } from 'src/app/classes/Card';

@Component({
  selector: 'app-cardinfo',
  templateUrl: './cardinfo.component.html',
  styleUrls: ['./cardinfo.component.css']
})

@Injectable({
  providedIn: 'root'
})


export class CardinfoComponent implements OnInit {
  @Input() cardImage: string
  @Input() card:Card

  constructor() { }
  
  //msg:string

  ngOnInit() {
    
  }


}
