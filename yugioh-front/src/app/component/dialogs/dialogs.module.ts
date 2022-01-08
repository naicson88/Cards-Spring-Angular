import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ErrorDialogComponent } from './error-dialog/error-dialog.component';
import { MatDialogModule} from '@angular/material';
import { MaterialModule } from 'src/app/Util/materialModule/material/material.module';
import { WarningDialogComponent } from './warning-dialog/warning-dialog.component';
import { SuccessDialogComponent } from './success-dialog/success-dialog.component';


@NgModule({
  declarations: [ErrorDialogComponent, WarningDialogComponent, SuccessDialogComponent],
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
  entryComponents:[ErrorDialogComponent, WarningDialogComponent, SuccessDialogComponent] 
})

export class DialogModule { }