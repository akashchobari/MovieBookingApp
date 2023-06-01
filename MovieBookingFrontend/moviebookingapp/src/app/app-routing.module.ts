import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthguardService } from './authguard.service';
import { BookComponent } from './book/book.component';
import { ForgotpasswordComponent } from './forgotpassword/forgotpassword.component';
import { LoginComponent } from './login/login.component';
import { MoviesComponent } from './movies/movies.component';
import { RegisterComponent } from './register/register.component';

const routes: Routes = [
  { path:"register", component:RegisterComponent},
  { path:"login", component:LoginComponent},
  { path:"forgot-password", component:ForgotpasswordComponent},
  { path:"movies", component:MoviesComponent, canActivate:[AuthguardService]},
  { path:"book", component:BookComponent, canActivate:[AuthguardService]},
  { path:"", redirectTo: "/login", pathMatch: 'full'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
