import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DeckDetailUserComponent } from './deck-detail-user.component';
import { AppModule } from 'src/app/app.module';
import { MaterialModule } from 'src/app/Util/materialModule/material/material.module';

@NgModule({
  declarations: [DeckDetailUserComponent],
  imports: [
    CommonModule,
    MaterialModule,
  ],
  exports:[
    
  ]
})

export class DeckDetailUserModule { }