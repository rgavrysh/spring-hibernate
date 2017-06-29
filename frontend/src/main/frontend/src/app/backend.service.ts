import { Injectable } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import { AuthService } from './auth.service';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

import { User } from './user';

@Injectable()
export class BackendService {
  errorMessage: string;
  private authentication: {};
  private user: {};

  constructor(private http: Http, private authService: AuthService) { }

  getUsers(): Observable<User[]> {
    var headers = new Headers();
    var token = 'Bearer ' + this.authService.token;
    headers.append('Authorization', token);
    headers.append('Content-Type', 'application/json');
    return this.http.get('http://localhost:8080/customers', { headers: headers })
                    .map(response => response.json());
  }

  deleteUser(userId: number){
    var headers = new Headers();
    var token = 'Bearer ' + this.authService.token;
    headers.append('Authorization', token);
    headers.append('Content-Type', 'application/json');
    return this.http.delete('http://localhost:8080/customer/'+userId+'/delete', { headers: headers })
      .map((response: Response) => {
      	if (response.ok){
      	  return true;
      	}
      });
  }

  deleteBooking(bookingId: number){
    var headers = new Headers();
    var token = 'Bearer ' + this.authService.token;
    headers.append('Authorization', token);
    headers.append('Content-Type', 'application/json');
    return this.http.delete('http://localhost:8080/bookings/'+bookingId, { headers: headers })
      .map((response: Response) => {
        if (response.ok){
          return true;
        }
      });
  }

  addUser(user: User) {
    var headers = new Headers();
    var token = 'Bearer ' + this.authService.token;
    headers.append('Authorization', token);
    headers.append('Content-Type', 'application/json');
    var body = JSON.stringify(user);
    return this.http.post('http://localhost:8080/customer', body, { headers: headers })
      .map((response: Response) => {
        if (response.ok){
          return true;
        }
      })
  }

  getVenues() {
    var headers = new Headers();
    var token = 'Bearer ' + this.authService.token;
    headers.append('Authorization', token);
    headers.append('Content-Type', 'application/json');
    return this.http.get('http://localhost:8080/venues', { headers: headers })
                    .map(response => response.json());
  }

  getBookings() {
    var headers = new Headers();
    var token = 'Bearer ' + this.authService.token;
    headers.append('Authorization', token);
    headers.append('Content-Type', 'application/json');
    return this.http.get('http://localhost:8080/me/bookings/venue', {headers: headers})
      .map(response => response.json());
  }

  bookVenue(id: number, body: Object){
    var headers = new Headers();
    var token = 'Bearer ' + this.authService.token;
    headers.append('Authorization', token);
    headers.append('Content-Type', 'application/json');
    return this.http.post('http://localhost:8080/bookings/venue/'+id+'/bookTime', body, {headers: headers})
      .map((response: Response) => {
        if (response.ok){
          return true;
        }
      })
  }

  aboutMe() {
    var headers = new Headers();
    var token = 'Bearer ' + this.authService.token;
    headers.append('Authorization', token);
    headers.append('Content-Type', 'application/json');
    return this.http.get('http://localhost:8080/me', {headers: headers})
      .map(response => response.json());
  }

  private extractData(res: Response) {
    let body = res.json();
    return body.data || { };
  }

  private handleError (error: Response | any) {
    let errMsg: string;
    if (error instanceof Response) {
      const body = error.json() || '';
      const err = body.error || JSON.stringify(body);
      errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
    } else {
      errMsg = error.message ? error.message : error.toString();
    }
    console.error(errMsg);
    return Observable.throw(errMsg);
  }
}
