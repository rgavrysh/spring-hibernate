import { Component, OnInit } from '@angular/core';
import { BackendService } from '../backend.service';
import { Venue } from '../venue';
declare var componentHandler: any;

@Component({
  selector: 'app-venues',
  templateUrl: './venues.component.html',
  styleUrls: ['./venues.component.css']
})
export class VenuesComponent implements OnInit {

  constructor(private backendService: BackendService) { }

  venues: Venue[];
  /* = [new Venue(1, 'grass', 123, '09:30', '22:00'), new Venue(2, 'AG', 456, '08:00', '23:00')];*/
  errorMsg: string;
  isOpen = 'none';
  modalStartWork: string = '';
  modalEndWork: string = '';
  bookStartTime: string;
  bookEndTime: string;
  currentVenueId: number;

  ngOnInit() {
    this.getVenues();
    componentHandler.upgradeDom();
  }

  getVenues() {
    this.backendService.getVenues()
      .subscribe(
        res => this.venues = res,
        err => this.errorMsg = <any> err);
  }

  displayModal(item: Venue){
  	this.modalStartWork = item.startWork;
  	this.modalEndWork = item.endWork;
  	this.currentVenueId = item.id;
    this.isOpen = 'block';
  }

  closeModal(){
    this.isOpen = 'none';
  }

  submitTime(){
    console.log(this.bookStartTime);
    console.log(this.bookEndTime);
    let body = {'start_date_time': this.bookStartTime,
    			'end_date_time': this.bookEndTime};
    this.backendService.bookVenue(this.currentVenueId, body)
      .subscribe(
        res => {
          if (res){
            this.closeModal();
            var data = {message: 'Successfully booked.', timeout: 3000};
            var snackbar = document.querySelector('#snackbar-message');
            (snackbar as any).MaterialSnackbar.showSnackbar(data);
          }
        },
        err => {
          this.errorMsg = err.json().message;
          var data = {message: 'Time slot is not available.', timeout: 4000};
          var snackbar = document.querySelector('#snackbar-message');
          (snackbar as any).MaterialSnackbar.showSnackbar(data);
        });
  }
}
