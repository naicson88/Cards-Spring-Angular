import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MaterialModule } from 'src/app/Util/materialModule/material/material.module';
import {DragDropModule} from '@angular/cdk/drag-drop';


@NgModule({
  declarations: [  ],
  imports: [
    CommonModule,
    MaterialModule,
    DragDropModule,

  ],
  exports:[
    CommonModule,
    MaterialModule,
    DragDropModule,
  ]
})

export class DeckDetailUserModule { }