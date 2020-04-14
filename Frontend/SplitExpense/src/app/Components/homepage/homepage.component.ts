import { Component, OnInit } from '@angular/core';
import {User} from '../../classes/user';
import {LoginSignupService} from '../../services/login-signup.service';
import {Router} from '@angular/router';
import {UserServiceService} from '../../services/user-service.service';
import {MatDialog} from '@angular/material/dialog';
import {MessageDialogComponent} from "../../dialogComponent/message-dialog/message-dialog.component";

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {

  private passwordHide = true;
  private loginUser: User;
  private signUpUser: User;
  constructor(private loginSignupService: LoginSignupService, private router: Router, private userService: UserServiceService, private dialog: MatDialog) { }

  ngOnInit() {
    this.loginUser = new User();
    this.signUpUser = new User();
  }

  addUser() {
    this.loginSignupService.addUser(this.signUpUser).subscribe(
      data => {
        console.log('SignUp method data');
        console.log(data);
        if (data['statusCode'] === 201) {
          this.showDialog('Yipiee!!!', 'User account created successfully');
          this.signUpUser = new User();
        } else if (data['statusCode'] === 400){
          this.showDialog('User registration Failed', data['message']);
        } else if (data['statusCode'] === 500){
          this.showDialog('Server Error', data['message']);
        }
      },
      error => {
        console.log('SignUp method error');
        console.log(error);
        this.showDialog('Remote Server Error', 'Error occured while registering user. Kindly contact system administrator');
      }
    );

  }

  login() {
    console.log('Login Started');
    this.loginSignupService.login(this.loginUser).subscribe(
      data => {
        console.log('Login Method Data:');
        console.log(data);
        if (data['statusCode'] === 200){
          console.log('Login Successful');
          this.userService.setterToken(data['token']);
          this.userService.setterUser(data['user']);
          this.userService.setterGroupList(data['groupList']);
          this.userService.setterFriendsList(data['friendList']);
          this.loginUser = new User();
          this.router.navigate(['/dashboard']);
        } else if (data['statusCode'] === 601) {
          this.showDialog('Authentication Failed', 'Username and/or password entered is incorrect');
        } else if (data['statusCode'] === 400){
          this.showDialog('Authentication Failed', data['message']);
        } else if (data['statusCode'] === 500){
          this.showDialog('Server Error', 'Error occured while logging in. Kindly contact system administrator');
        }
      },
      error => {
        this.showDialog('Remote Server Error', 'Error occured while logging in. Kindly try after sometime');
      }
    );
  }

  showDialog(title: string, content: string) {
    this.dialog.open(MessageDialogComponent, {data: {title, content}});
  }

}
