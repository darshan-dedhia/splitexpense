import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';
import {UserRelatedService} from '../../services/user-related.service';
import {MatSnackBar} from "@angular/material/snack-bar";
import {UserServiceService} from "../../services/user-service.service";

@Component({
  selector: 'app-add-friend',
  templateUrl: './add-friend.component.html',
  styleUrls: ['./add-friend.component.scss']
})
export class AddFriendComponent implements OnInit {
  private username: string
  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
              private userRelatedService: UserRelatedService,
              private snackbar: MatSnackBar,
              private dialogRef: MatDialogRef<AddFriendComponent>,
              private userService: UserServiceService) { }

  ngOnInit() {
  }


  onSubmit($event) {
    if (this.validate()) {
      this.userRelatedService.addFriend(this.username).subscribe(data => {
        console.log(data);
        if (data['statusCode'] === 200) {
            this.snackbar.open('Yippie! We got your friend added', 'Dismiss', {duration: 3000});
            this.userService.getterFriendsList().push(data['object']);
            this.dialogRef.close(true);
        } else if (data['statusCode'] === 400) {
            this.snackbar.open(`${data['message']}`, 'Dismiss', {duration: 3000});
        } else {
          this.snackbar.open('Server error occured', 'Dismiss', {duration: 3000});
        }
      });
    }
  }

  validate() {
    if (this.username === '' || this.username === undefined || this.username === null){
      this.snackbar.open('Username is required', 'Dismiss', {duration: 3000});
      return false;
    }
    return true;
  }

  onCancel() {
    this.dialogRef.close(false);
  }

}
