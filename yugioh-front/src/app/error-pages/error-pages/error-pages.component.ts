import { AfterContentInit, AfterViewChecked, AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-error-pages',
  templateUrl: './error-pages.component.html',
  styleUrls: ['./error-pages.component.css']
})
export class ErrorPagesComponent implements OnInit, AfterViewChecked {
  @ViewChild('img',{static: false})img:HTMLElement;

  constructor() { }

  ngAfterViewChecked(): void {
    this.scroll()
  }


  ngOnInit() {
   
  }

  scroll(){
   window.scrollTo(0,250)
  }


}
