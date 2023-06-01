export class Movie{
    constructor(
        public movieId:number,
        public movieName:string,
        public theatreName:string,
        public availableSeats:number,
        public bookedSeats:number,
        public status:string
    ){}
}