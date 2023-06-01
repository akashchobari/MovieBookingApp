import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { UpdateForm } from '../model/updateForm';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-forgotpassword',
  templateUrl: './forgotpassword.component.html',
  styleUrls: ['./forgotpassword.component.css']
})
export class ForgotpasswordComponent {
  myUserName:string = '';
  secretQuestion:string = '';
  constructor(private formBuilder:FormBuilder, private authService:AuthService, private router:Router){}

  forgotRequestForm = this.formBuilder.group({
    userName:[''],
  });

  updateForm = this.formBuilder.group({
    userName:[''],
    secretAnswer:[''],
    newPassword:['']
  });

  getSecretQuestion(){
    this.authService.getSecretQuestion(this.forgotRequestForm.value.userName as string)
      .subscribe({
        next: (data)=>{
          console.log(data);
          this.secretQuestion = data as string;
          this.myUserName = this.forgotRequestForm.value.userName as string;
        },
        error: (errorObj)=>{
          alert("Bad Input, Please enter valid input");
        }
      });
  }

  updatePassword(){
    this.updateForm.value.userName = this.myUserName;
    this.authService.updatePassword(this.updateForm.value as UpdateForm)
      .subscribe({
        next: (data)=>{
          alert(data);
          this.router.navigate(["/login"]);
        },
        error: (errorObj)=>{
          if(errorObj.status==401)
            alert("Bad Credentials");
          else
            alert("Internal Server Error")
        }
      })
  }

  getUserName(){
    return this.myUserName;
  }

  getFetchedSecretQuestion(){
    return this.secretQuestion;
  }
}
