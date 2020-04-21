import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {UserServiceService} from '../../services/user-service.service';
import {Group} from '../../classes/group';
import {User} from '../../classes/user';
import {MatDialog} from "@angular/material/dialog";
import {AddModifyGroupComponent} from "../add-modify-group/add-modify-group.component";
import {AddFriendComponent} from "../add-friend/add-friend.component";

@Component({
  selector: 'app-side-navigation',
  templateUrl: './side-navigation.component.html',
  styleUrls: ['./side-navigation.component.scss']
})
export class SideNavigationComponent implements OnInit {
  private groupList: Group[];
  private friendsList: User[];
  constructor(private router: Router, private userService: UserServiceService, private dialog: MatDialog) {
    this.groupList = this.userService.getterGroupList();
    this.friendsList = this.userService.getterFriendsList();
  }

  ngOnInit() {
  }

  addUpdateGroup() {
      this.dialog.open(AddModifyGroupComponent, {data: {friendList: this.friendsList, group: new Group(this.userService.getterUser()), title: 'Create Group', call: 'ADD'}});
  }

  addFriend() {
      this.dialog.open(AddFriendComponent, {data: {title: 'Add friend'}});
  }

}
