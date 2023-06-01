import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ActivatedRouteSnapshot, CanActivate, CanActivateFn, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { Movie } from '../model/movie';
import { MovieService } from '../services/movie.service';

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.css']
})
export class MoviesComponent{
  userName:string='';
  dataSource:Movie[]=[];
  movies:Movie[]=[];
  searchMovies:Movie[]=[];
  displayedColumns:any=[];
  panelOpenState=false;
  
  constructor(private movieService:MovieService, private router:Router, private formBuilder:FormBuilder){
    this.getAllMovies();
    this.userName=sessionStorage.getItem('userName') as string;
    
    if(this.userName=="admin"){
      this.displayedColumns = ['movieId', 'movieName', 'theatreName', 'availableSeats', 'bookedSeats', 'status','book','delete'];
    }
    else{
      this.displayedColumns = ['movieId', 'movieName', 'theatreName', 'availableSeats', 'bookedSeats', 'status','book'];
    }
  }
  
  addMovieForm = this.formBuilder.group({
    movieId:null,
    movieName:[''],
    theatreName:[''],
    availableSeats:[100],
    bookedSeats:[0],
    status:['AVAILABLE'],
  });

  handleSearch(searchName:string){
    if(searchName=='')
      this.dataSource=this.movies;
    this.dataSource = this.movieService.getFilteredMovies(searchName,this.movies);
  }

  getAllMovies(){
    this.movieService.getAllMovies()
      .subscribe({
        next: (data)=>{
          this.dataSource = data;
          this.searchMovies = data;
          this.movies=data;
          console.log(this.dataSource);
          
        },
        error: (errorObj)=>{
          alert(errorObj.message);
        }
      })
  }

  handleBook(movie:Movie){
    this.router.navigate(["/book"], {state: {selectedMovie:movie}});
  }

  handleDelete(movie:Movie){
    this.movieService.deleteMovie(movie.movieId)
      .subscribe({
        next: (data)=>{
          alert(data);
          window.location.reload();
        },
        error: (errorObj)=>{
          alert(errorObj.message);
        }
      })
  }

  addMovie(){
    let movie = this.addMovieForm.value;
    this.movieService.addMovie(movie)
      .subscribe({
        next: (data)=>{
          alert(data);
          window.location.reload();
        },
        error: (errorObj)=>{
          alert(errorObj.message);
        }
      });
  }

}
