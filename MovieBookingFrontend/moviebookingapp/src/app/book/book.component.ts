import { Component } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { Movie } from '../model/movie';
import { MovieService } from '../services/movie.service';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent {
  selectedMovie:Movie = new Movie(0,'','',0,0,'');
  constructor(private router:Router, private formBuilder:FormBuilder, private movieService:MovieService){
    this.selectedMovie = router.getCurrentNavigation()?.extras.state?.['selectedMovie'];
  }

  bookingForm = this.formBuilder.group({
    movieName:[this.selectedMovie.movieName],
    theatreName:[this.selectedMovie.theatreName],
    noOfTickets:0
  });

  handleBookTicket(){
    this.movieService.bookTickets(this.selectedMovie.movieName,this.selectedMovie.movieId,this.bookingForm.value.noOfTickets as number)
      .subscribe({
        next: (data)=>{
          alert(data);
          this.router.navigate(['/movies']);
        }, 
        error: (errorObj)=>{
          alert(errorObj.message);
        }
      });
  }

}
