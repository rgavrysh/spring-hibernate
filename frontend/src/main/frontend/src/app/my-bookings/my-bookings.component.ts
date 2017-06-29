import { Component, OnInit } from '@angular/core';
import {Booking } from '../booking';
import { BackendService } from '../backend.service';

@Component({
  selector: 'app-my-bookings',
  templateUrl: './my-bookings.component.html',
  styleUrls: ['./my-bookings.component.css']
})
export class MyBookingsComponent implements OnInit {

  constructor(private backendService: BackendService) { }

  bookings: Booking[];
  errorMsg: string;

  ngOnInit() {
    this.getBookings();
  }

  getBookings() {
    this.backendService.getBookings()
      .subscribe(
        res => this.bookings = res,
        err => this.errorMsg = <any>err);
  }

  checkbox(){
    console.log(this.bookings);
  }

  dateParser(date: number): string{
    return new Date(date).toLocaleString();
  }

  deleteBooking() {
    let toDelete = (this.bookings as any).filter((booking) => booking.selected);
    console.log(toDelete);
    for (let i = 0; i < toDelete.length; i++){
      this.backendService.deleteBooking(toDelete[i].id)
        .subscribe(
          res => {if (res){this.getBookings();}},
          error => this.errorMsg = <any>error);
    }
  }

}
