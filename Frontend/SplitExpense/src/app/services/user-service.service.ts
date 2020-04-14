import { Injectable } from '@angular/core';
import {User} from '../classes/user';
import {Group} from '../classes/group';

@Injectable({
  providedIn: 'root'
})
export class UserServiceService {
  private user: User;
  private token: string;
  private groupList: Group[];
  private friendsList: User[];

  constructor() {
    this.friendsList = new Array<User>();
    this.groupList = new Array<Group>();
  }

  setterUser(user: User) {
      this.user = user;
  }

  getterUser() {
    return this.user;
  }

  setterToken(token: string) {
    this.token = token;
  }

  getterToken() {
    return this.token;
  }

  setterGroupList(groupList: Group[]) {
    this.groupList = groupList;
  }

  getterGroupList() {
    return this.groupList;
  }

  setterFriendsList(friendsList: User[]) {
    this.friendsList = friendsList;
  }

  getterFriendsList() {
    return this.friendsList;
  }

}
