import { AfterContentInit, AfterViewChecked, AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';

@Component({
  selector: 'app-error-pages',
  templateUrl: './error-pages.component.html',
  styleUrls: ['./error-pages.component.css']
})
export class ErrorPagesComponent implements OnInit, AfterViewInit, AfterViewChecked {
  @ViewChild('img',{static: false})img:HTMLElement;

  constructor() { }

  ngAfterViewChecked(): void {
   this.scroll()
  }

  ngAfterContentInit(): void {
  this.scroll2(this.img)
  }

  ngOnInit() {
   
  }

  ngAfterViewInit(): void {
      
  }

  scroll(){
   window.scrollTo(0,250)
  }

  scroll2(el: HTMLElement) {
    el.scrollIntoView();
}

}
