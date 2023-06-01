import { HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Movie } from '../model/movie';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  constructor(private http:HttpClient) {
    
  }
  
  getAllMovies(){
    console.log(sessionStorage.getItem("authToken") as string);
    let headers = new HttpHeaders();
    headers.set("Authorization","Bearer "+sessionStorage.getItem('authToken' as string));
    return this.http.get<Movie[]>("http://localhost:8081/api/v1/moviebooking/all", {headers: {'Authorization':"Bearer "+sessionStorage.getItem('authToken')}});
  }

  bookTickets(movieName:string,movieId:number, numberOfTickets:number){
    return this.http.post("http://localhost:8081/api/v1/moviebooking/"+movieName+"/book",{movieId, numberOfTickets}, {headers:{"Authorization": "Bearer "+sessionStorage.getItem('authToken')}, responseType:"text"});
  }

  getFilteredMovies(searchName:string, movies:Movie[]){
    let filterMovies:Movie[] = [];
    for(let movie of movies){
      if((movie.movieName.toLowerCase()).includes(searchName.toLowerCase())){
        filterMovies.push(movie);
      }
    }
    return filterMovies;
  }

  deleteMovie(movieId:number){
    return this.http.delete("http://localhost:8081/api/v1/moviebooking/admin/delete/"+movieId,{headers: {'Authorization':"Bearer "+sessionStorage.getItem('authToken')}, responseType:"text"});
  }

  addMovie(movie:any){
    return this.http.post("http://localhost:8081/api/v1/moviebooking/admin/addmovie",movie,{headers: {'Authorization':"Bearer "+sessionStorage.getItem('authToken')}, responseType:"text"});
  }
}
