import { Component } from '@angular/core';

@Component({
  selector: 'pitch-app',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {
  public menuItems = [
    { caption: 'Home', link: '/home' },
    { caption: 'Venues', link: '/venues' },
    { caption: 'admin', link: '/admin'}
  ];


}
