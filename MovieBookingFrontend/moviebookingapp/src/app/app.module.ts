import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatDialogModule } from '@angular/material/dialog';
import {MatButtonModule} from '@angular/material/button';
import {MatIconModule} from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { RegisterComponent } from './register/register.component';
import {MatInputModule} from '@angular/material/input';
import {MatCardModule} from '@angular/material/card';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import {MatRadioModule} from '@angular/material/radio';
import { HttpClientModule } from '@angular/common/http';
import { LoginComponent } from './login/login.component';
import { ForgotpasswordComponent } from './forgotpassword/forgotpassword.component';
import { MoviesComponent } from './movies/movies.component';
import {MatTableModule} from '@angular/material/table';
import { BookComponent } from './book/book.component';
import {MatExpansionModule} from '@angular/material/expansion';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    ForgotpasswordComponent,
    MoviesComponent,
    BookComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatDialogModule,
    MatButtonModule,
    MatIconModule,
    MatToolbarModule,
    MatInputModule,
    MatCardModule,
    FormsModule,
    ReactiveFormsModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatRadioModule,
    HttpClientModule,
    MatTableModule,
    MatExpansionModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
