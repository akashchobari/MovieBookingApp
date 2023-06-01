import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from '../model/user';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  constructor(private formBuilder:FormBuilder, private authService:AuthService, private router:Router){}

  userProfileForm = this.formBuilder.group({
    firstName:[''],
    lastName:[''],
    userName:[''],
    password:[''],
    email:[''],
    gender:[''],
    userRole:['CUSTOMER'],
    dob:[''],
    secretQuestion:[''],
    secretAnswer:[''],
    mobileNumber:['']
  });

  saveForm(){
    this.authService.register(this.userProfileForm.value as User)
      .subscribe({
        next: (data)=>{
          alert(data);
          this.router.navigate(['/login']);
        },
        error: (errorObj)=>{
          alert(errorObj.message);
        }
      });
  }
}
