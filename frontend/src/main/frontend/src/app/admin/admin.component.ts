import { Component, OnInit } from '@angular/core';
import { BackendService } from '../backend.service';
import { AuthGuard } from '../auth.guard';
import { User } from '../user';
declare var componentHandler: any;

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  errorMessage: string;
  successMsg: string;
  users: User[];
  response: {};
  isOpen = 'none';
  newUser: User = new User('', '');

  constructor( private backendService: BackendService ) { }

  ngOnInit() {
    this.getUsers();
    console.log(this.users);
    componentHandler.upgradeDom();
  }

  getUsers() {
    this.backendService.getUsers()
      .subscribe(
        res => this.users = res,
        error =>  this.errorMessage = <any>error);
  }

  deleteUsers() {
    let toDelete = (this.users as any).filter((user) => user.selected);
    console.log(toDelete);
    for (let i = 0; i < toDelete.length; i++){
      this.backendService.deleteUser(toDelete[i].id)
        .subscribe(
          res => {if (res){this.getUsers();}},
          error => this.errorMessage = <any>error);
    }
  }

  displayModal() {
    this.isOpen = 'block';
  }

  closeModal() {
    this.isOpen = 'none';
  }

  onSubmit() {
    console.log(JSON.stringify(this.newUser));
    this.backendService.addUser(this.newUser)
      .subscribe(
        res => {
          if(res){
            this.getUsers();
            this.closeModal();
            this.newUser = new User('', '');
          }
        },
        error => this.errorMessage = <any>error);
  }
}
