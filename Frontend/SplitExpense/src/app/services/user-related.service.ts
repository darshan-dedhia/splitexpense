import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {GlobalVariablesService} from './global-variables.service';

@Injectable({
  providedIn: 'root'
})
export class UserRelatedService {

  constructor(private http: HttpClient, private global: GlobalVariablesService) { }

  addFriend(friendUserName: string) {
    return this.http.get(this.global.getUrl() + `/users/addFriend/${friendUserName}`, {headers: this.global.getDefaultHttpHeader()});
  }

  sendMail(to: string, subject: string, body: string) {
    const mail = {to, subject, body};
    return this.http.post(this.global.getUrl() + '/users/mail', mail, {headers: this.global.getDefaultHttpHeader()});
  }

}
