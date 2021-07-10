import { Component, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { FormGroup } from '@angular/forms';
import { Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/service/auth-service/auth.service';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
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
      password: ['', Validators.required],
      email: ['', Validators.required]
    });
  }
  get f() {
    return this.form.controls
  }

  isFieldInvalid(field: string){
    return (
      (!this.form.get(field).valid && this.form.get(field).touched) ||
      (!this.form.get(field).untouched && this.formSubmitAttempt)
    );
  }

  onSubmit(){
    this.authService.signup(
      {
        email: this.f.email.value,
        password: this.f.password.value,
        userName: this.f.userName.value
      }
    ).subscribe(() => this.router.navigate([this.authService.CONFIRM_PATH]))
  }

}
