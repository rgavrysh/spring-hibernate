import { Injectable, Inject } from '@angular/core';
import { Http, Headers, Response, URLSearchParams } from '@angular/http';
import { OauthToken } from './oauth-token';
import { APP_SETTINGS, IAppSettings } from './app.settings';

import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';

@Injectable()
export class AuthService {

  constructor(@Inject(APP_SETTINGS) private config: IAppSettings, private http: Http) { }

  oauthToken: OauthToken;
  token = '';
  loggedIn = false;
  isAdmin: boolean;
  errorMessage: string;

  authenticate(username: string, password: string): Observable<boolean> {
    let body = new URLSearchParams();
    body.set('username', username);
    body.set('password', password);
    body.set('grant_type', 'password');
    var headers = new Headers();
    headers.append('Content-Type', 'application/x-www-form-urlencoded');
    headers.append('Authorization', 'Basic ' + btoa(this.config.securityClientName + ':' + this.config.securityClientSecret));
    return this.http.post(this.config.apiProtocol + '://' + this.config.apiHost + ':' + this.config.apiPort + '/oauth/token',
      body.toString(), { headers: headers })
      .map((response: Response) => {
        this.oauthToken = response.json();
        if (this.oauthToken.access_token){
          this.loggedIn = true;
          this.token = this.oauthToken.access_token;
          return true;
        }
      });
  }

  logout(): void {
  	this.loggedIn = false;
  }

}
