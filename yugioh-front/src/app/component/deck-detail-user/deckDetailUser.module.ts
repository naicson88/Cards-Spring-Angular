import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DeckDetailUserComponent } from './deck-detail-user.component';
import { AppModule } from 'src/app/app.module';
import { MaterialModule } from 'src/app/Util/materialModule/material/material.module';
import {DragDropModule} from '@angular/cdk/drag-drop';

@NgModule({
  declarations: [DeckDetailUserComponent],
  imports: [
    CommonModule,
    MaterialModule,
    DragDropModule

  ],
  exports:[
    
  ]
})

export class DeckDetailUserModule { }