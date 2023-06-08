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
    return this.http.post("https://wg6zgh6lhh.execute-api.us-west-2.amazonaws.com/v1/user/register",user,{responseType: 'text'});
  }

  login(userLogin:LoginRequest){
    return this.http.post<LoginResponse>("https://wg6zgh6lhh.execute-api.us-west-2.amazonaws.com/v1/user/login",userLogin);
  }

  getSecretQuestion(userName:string){
    return this.http.get("https://wg6zgh6lhh.execute-api.us-west-2.amazonaws.com/v1/user/"+userName, {responseType: 'text'});
  }

  updatePassword(updateForm:UpdateForm){
    return this.http.put("https://wg6zgh6lhh.execute-api.us-west-2.amazonaws.com/v1/user/"+updateForm.userName,updateForm, {responseType: 'text'});
  }
}
