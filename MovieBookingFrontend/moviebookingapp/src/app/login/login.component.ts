import { Component, EventEmitter, Output } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginResponse } from '../model/loginResponse';
import { LoginRequest } from '../model/userLogin';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  constructor(private formBuilder:FormBuilder, private authService:AuthService, private router:Router){}

  @Output() isLoggedIn = new EventEmitter<boolean>();
  loginForm = this.formBuilder.group({
    userName:[''],
    password:['']
  });

  doLogin(){
    console.log(this.loginForm.value);
    this.authService.login(this.loginForm.value as LoginRequest)
      .subscribe({
        next: (data)=>{
          sessionStorage.setItem("isAuthenticated","true");
          sessionStorage.setItem("authToken",data.token as string);
          sessionStorage.setItem("userName",this.loginForm.value.userName as string);
          alert(data.message);
          this.router.navigate(["/movies"]);
        },
        error: (errorObj)=>{
          if(errorObj.status==401)
            alert("Please enter valid credentials");
          else 
            alert("internal server error: 500, please try again");
        }
      })
  }
}
