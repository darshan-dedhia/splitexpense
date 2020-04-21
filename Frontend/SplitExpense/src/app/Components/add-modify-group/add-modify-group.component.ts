import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {UserServiceService} from '../../services/user-service.service';
import {Group} from '../../classes/group';
import {User} from '../../classes/user';
import {MatSnackBar} from "@angular/material/snack-bar";
import {GroupService} from "../../services/group.service";

@Component({
  selector: 'app-add-modify-group',
  templateUrl: './add-modify-group.component.html',
  styleUrls: ['./add-modify-group.component.scss']
})
export class AddModifyGroupComponent implements OnInit {
  private groupMaster: Group;  //This stores data on initialization
  private group: Group;
  private call: string; // This flag decides whether its a ADD call or UPDATE call
  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
              private userService: UserServiceService,
              private snackbar: MatSnackBar,
              private groupService: GroupService,
              private dialogRef: MatDialogRef<AddModifyGroupComponent>) {
      this.group = data.group;
      this.groupMaster = data.group;
      this.call = data.call;
      if (this.call === 'UPDATE') {
        this.group = new Group(null);
        Object.assign(this.group, data.group);
        console.log(this.group);
      }

  }

  ngOnInit() {
  }

  checkUserInGroup(userIp: User) {
    return this.group.userList.find((user) => user.userId === userIp.userId);
  }

  userAddRemove(event: any, userIp: User) {
    if (event.checked && !this.group.userList.find((user) => user.userId === userIp.userId)) {
      this.group.userList.push(userIp);
    } else if (!event.checked && this.group.userList.find((user) => user.userId === userIp.userId)) {
      const index = this.group.userList.indexOf(this.group.userList.find((user) => user.userId === userIp.userId));
      if (index !== -1) { this.group.userList.splice(index, 1); }

    } else {
      console.log('No Change');
    }
  }

  createGroup() {
   if (this.group.groupName === undefined || this.group.groupName === null || this.group.groupName === ''){
      this.snackbar.open('Ensure proper group name', 'Dismiss', {duration: 3000});
      return;
   }
   console.log(this.group);
   this.groupService.addUpdateGroup(this.group).subscribe(data => {
        if (data['statusCode'] === 200) {
          if (this.call === 'ADD') {
            this.userService.getterGroupList().push(data['object']);
            this.snackbar.open('Group created successfully', 'Dismiss', {duration: 2000});
          } else {
            this.groupMaster.groupName = this.group.groupName;
            this.groupMaster.groupId = this.group.groupId;
            this.groupMaster.userList = this.group.userList;
            const index = this.userService.getterGroupList().findIndex((group) => group.groupId === this.group.groupId);
            if (index !== -1) {this.userService.getterGroupList().splice(index, 1, data['object']);}
            this.snackbar.open('Group updated successfully', 'Dismiss', {duration: 2000});
          }
          this.dialogRef.close(true);
        }
      });
  }

  cancelButton() {
    this.group = new Group(null);
    this.groupMaster = new Group(null);
    this.dialogRef.close(false);
  }

  stateAddUpdate () {
    return this.call === 'ADD';
  }
}
