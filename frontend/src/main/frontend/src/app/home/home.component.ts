import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(public authService: AuthService) { }
  token: string;

  ngOnInit() {
    if (this.authService.loggedIn){
      this.token = this.authService.token;
    }
  }
}
