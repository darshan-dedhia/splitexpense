import { Component, OnInit } from '@angular/core';
import {User} from '../../classes/user';
import {LoginSignupService} from '../../services/login-signup.service';
import {Router} from '@angular/router';
import {UserServiceService} from '../../services/user-service.service';
import {MatDialog} from '@angular/material/dialog';
import {MessageDialogComponent} from "../../dialogComponent/message-dialog/message-dialog.component";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {

  private passwordHide = true;
  private loginUser: User;
  private signUpUser: User;
  constructor(private loginSignupService: LoginSignupService,
              private router: Router,
              private userService: UserServiceService,
              private dialog: MatDialog,
              private snackbar: MatSnackBar) { }

  ngOnInit() {
    this.loginUser = new User();
    this.signUpUser = new User();
  }

  addUser() {
    if (!this.validateUser()) {return; }
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

  validateUser() {
    let emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    let userNamePattern = /^[a-z0-9]+$/i;


    if (!userNamePattern.test(this.signUpUser.userName) || this.signUpUser.userName === ''  || this.signUpUser.userName.trim().length === 0 || this.signUpUser.userName===null || this.signUpUser.userName===undefined) {
      this.snackbar.open('Username should be alphanumeric', 'Dismiss', {duration: 3000});
      return false;
    }

    if (this.signUpUser.password === ''  || this.signUpUser.password.trim().length === 0 || this.signUpUser.password === null || this.signUpUser.password === undefined) {
      this.snackbar.open('Password is empty', 'Dismiss', {duration: 3000});
      return false;
    }

    if (this.signUpUser.name === '' || this.signUpUser.name.trim().length === 0 || this.signUpUser.name === null || this.signUpUser.name === undefined) {
      this.snackbar.open('Name cannot be empty', 'Dismiss', {duration: 3000});
      return false;
    }

    if (!emailPattern.test(this.signUpUser.email) || this.signUpUser.email ==='' || this.signUpUser.email.trim().length === 0 || this.signUpUser.email === null || this.signUpUser.email === undefined) {
      this.snackbar.open('Enter valid Email ID', 'Dismiss', {duration: 3000});
      return false;
    }

    return true;
  }

  login() {
    if (this.loginUser.userName === '' ||  this.loginUser.userName.trim().length === 0 || this.loginUser.userName === null || this.loginUser.userName === undefined) {
      this.snackbar.open('Username is empty', 'Dismiss', {duration: 3000});
      return;
    }

    if (this.loginUser.password === '' || this.loginUser.password.trim().length === 0 || this.loginUser.password === null || this.loginUser.password === undefined) {
      this.snackbar.open('Password is empty', 'Dismiss', {duration: 3000});
      return;
    }

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
