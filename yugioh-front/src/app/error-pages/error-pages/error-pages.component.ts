import { AfterContentInit, AfterViewChecked, AfterViewInit, Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';

@Component({
  selector: 'app-error-pages',
  templateUrl: './error-pages.component.html',
  styleUrls: ['./error-pages.component.css']
})
export class ErrorPagesComponent implements OnInit, AfterViewChecked {
  @ViewChild('img',{static: false})img:HTMLElement;

  constructor(private router: ActivatedRoute) { }

  ngAfterViewChecked(): void {
   
  }

  errorImage:string;

  ngOnInit() {
    this.loadErrorImage()
  }

  scroll(){
   window.scrollTo(0,250)
  }

  loadErrorImage(){
    
      let event = this.router.snapshot.paramMap.get('code');
   
        if(event === '500'){
          this.errorImage = '..//..//..//assets//img//error//500.jpg';
          return;
        } 
        else if(event === '404'){
          this.errorImage = '..//..//..//assets//img//error//404.PNG'
          return;

        } else {
          this.errorImage = '..//..//..//assets//img//error//500.jpg';
          return;
        }
        
      
   
  }

}
