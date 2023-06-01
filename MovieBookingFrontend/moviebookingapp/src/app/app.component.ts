import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginComponent } from './login/login.component';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor(private router:Router){
  }
  title = 'moviebookingapp';

  isLoggedIn():boolean{
    if(sessionStorage.getItem('isAuthenticated')=='true')
      return true;
    return false;
  }

  handleLogout(){
    if(confirm("Are you sure, you need to logout?")){
      sessionStorage.setItem('userName','');
      sessionStorage.setItem('authToken','');
      sessionStorage.setItem('isAuthenticated','false');
      this.router.navigate(['/login']);
    }
  }
  
}
