import { Component, OnInit } from '@angular/core';
import {User} from '../../classes/user';
import {LoginSignupService} from '../../services/login-signup.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {

  private passwordHide = true;
  private loginUser: User;
  private signUpUser: User;
  constructor(private loginSignupService: LoginSignupService, private router: Router) { }

  ngOnInit() {
    this.loginUser = new User();
    this.signUpUser = new User();
  }

  addUser() {
    this.loginSignupService.addUser(this.signUpUser).subscribe(
      data => {
        console.log('SignUp method data');
        console.log(data);
      },
      error => {
        console.log('SignUp method error');
        console.log(error);
      }
    );
    this.signUpUser = new User();
  }

  login() {
    console.log('Login Started');
    this.loginSignupService.login(this.loginUser).subscribe(
      data => {
        console.log('Login Method Data:');
        console.log(data);
      },
      error => {
        console.log('Login Method Error:');
        console.log(error);
      }
    );
    this.loginUser = new User();
  }

}
