import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NgxSpinnerService } from "ngx-spinner";
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
//O interceptor permite esconder e mostrar o spinner toda vez que tiver uma requisição http 
export class SpinnerService  implements HttpInterceptor{

  constructor(private spinner: NgxSpinnerService)  { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
   //this.show();
   
     return next.handle(req).pipe(
      finalize( ()=> this.hide())
   );
   }

  public show(){
    this.spinner.show();
  }

  public hide(){
    this.spinner.hide();
  }
}
