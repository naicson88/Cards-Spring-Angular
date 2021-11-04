import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InfiniteScrollModule } from 'ngx-infinite-scroll';
import { UsercardsComponent } from './usercards.component';



@NgModule({
  declarations: [ ],
  imports: [
    CommonModule,
    InfiniteScrollModule
  ],
  exports:[
    InfiniteScrollModule
  ]
})

export class UsercardsModule { }
