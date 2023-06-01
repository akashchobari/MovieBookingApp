import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginResponse } from '../model/loginResponse';
import { UpdateForm } from '../model/updateForm';
import { User } from '../model/user';
import { LoginRequest } from '../model/userLogin';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http:HttpClient) { }

  register(user:User){
    return this.http.post("http://localhost:8082/api/auth/moviebooking/register",user,{responseType: 'text'});
  }

  login(userLogin:LoginRequest){
    return this.http.post<LoginResponse>("http://localhost:8082/api/auth/moviebooking/login",userLogin);
  }

  getSecretQuestion(userName:string){
    return this.http.get("http://localhost:8082/api/auth/moviebooking/forgot/"+userName, {responseType: 'text'});
  }

  updatePassword(updateForm:UpdateForm){
    return this.http.put("http://localhost:8082/api/auth/moviebooking/forgot/"+updateForm.userName+"/updatepassword",updateForm, {responseType: 'text'});
  }
}
