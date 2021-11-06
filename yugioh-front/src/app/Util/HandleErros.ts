
import { HttpErrorResponse } from '@angular/common/http';
import { throwError } from 'rxjs';


export abstract class HandleErros  {

    public static handleError(error: HttpErrorResponse) {

        if(error.error instanceof ErrorEvent){
            console.log(" An error occurred", error.error.message);
        } else {
            console.log(`Backend returned code  ${error.status} ` + ` body was: ${error.error} `);            
        }
        
        return throwError('Something bad happened, please try again later.');
    }

    
}
