import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {UserServiceService} from '../../services/user-service.service';
import {Group} from '../../classes/group';
import {User} from '../../classes/user';

@Component({
  selector: 'app-side-navigation',
  templateUrl: './side-navigation.component.html',
  styleUrls: ['./side-navigation.component.scss']
})
export class SideNavigationComponent implements OnInit {
  private groupList: Group[];
  private friendsList: User[];
  constructor(private router: Router, private userService: UserServiceService) {
    this.groupList = this.userService.getterGroupList();
    this.friendsList = this.userService.getterFriendsList();
  }

  ngOnInit() {
  }
}
