import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material';

@Component({
  selector: 'app-warning-dialog',
  templateUrl: './warning-dialog.component.html',
  styleUrls: ['./warning-dialog.component.css']
})
export class WarningDialogComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public warningMessage: string) { }

  ngOnInit() {
  }

}
