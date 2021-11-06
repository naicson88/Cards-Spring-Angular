import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ErrorDialogComponent } from './error-dialog/error-dialog.component';
import { MatDialogModule, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { MaterialModule } from 'src/app/Util/materialModule/material/material.module';
import { AppComponent } from 'src/app/app.component';
import { WarningDialogComponent } from './warning-dialog/warning-dialog.component';


@NgModule({
  declarations: [ErrorDialogComponent, WarningDialogComponent],
  imports: [
    CommonModule,
    MaterialModule,
    MatDialogModule
  ],
  exports:[
    
  ],
  providers: [
  /*{ provide: MAT_DIALOG_DATA, useValue: {} },
   { provide: MatDialogRef, useValue: {} }*/
  ],
  entryComponents:[ErrorDialogComponent, WarningDialogComponent] 
})

export class DialogModule { }