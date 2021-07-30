import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { retryWhen } from 'rxjs/operators';
import { LoginRequest } from 'src/app/classes/LoginRequest';
import { AuthService } from 'src/app/service/auth-service/auth.service';



@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: FormGroup;
  private formSubmitAttempt;
  
  constructor(
    private fb: FormBuilder,
   private authService: AuthService,
    private router: Router
    
  ) { }

  ngOnInit() {
    this.form = this.fb.group({
      userName: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  get f() {return this.form.controls}

  isFieldInvalid(field: string){
    return (
      (!this.form.get(field).valid && this.form.get(field).touched) ||
      (!this.form.get(field).untouched && this.formSubmitAttempt)
    );
  }

   onSubmit(){
    const loginRequest: LoginRequest = {
      username: this.f.userName.value,
      password: this.f.password.value
    };

  this.authService.login(loginRequest).subscribe(
      (user) => this.router.navigate([this.authService.INITIAL_PATH])
    );
  } 

}
