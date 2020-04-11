import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {User} from '../classes/user';

@Injectable({
  providedIn: 'root'
})
export class LoginSignupService {

  private baseUri = 'http://localhost:8080/finalproject';
  private headers = new HttpHeaders().set('Content-Type', 'application/json');

  constructor(private http: HttpClient) { }

  addUser(user: User) {
      return this.http.post(this.baseUri + '/users/addUser', user, {headers: this.headers});
  }

  login(user: User) {
    return this.http.post(this.baseUri + '/login', user, {headers: this.headers});
  }

}
