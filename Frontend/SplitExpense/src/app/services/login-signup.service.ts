import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from '../classes/user';
import {GlobalVariablesService} from './global-variables.service';

@Injectable({
  providedIn: 'root'
})
export class LoginSignupService {


  constructor(private http: HttpClient, private global: GlobalVariablesService) { }

  addUser(user: User) {
      return this.http.post(this.global.getUrl() + '/users/addUser', user, {headers: this.global.getDefaultHttpHeader()});
  }

  login(user: User) {
    return this.http.post(this.global.getUrl() + '/login', user, {headers: this.global.getDefaultHttpHeader()});
  }

}
