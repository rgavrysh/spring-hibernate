import { Component, OnInit } from '@angular/core';
import { BackendService } from '../backend.service';
import { User } from '../user';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  constructor(private backendService: BackendService) { }

  me: User;
  errorMsg: string;

  ngOnInit() {
    this.getUserInfo();
  }

  getUserInfo(){
    this.backendService.aboutMe()
      .subscribe(
        res => this.me = res,
        err => this.errorMsg = <any>err);
  }

}
