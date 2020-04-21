import { Component, OnInit } from '@angular/core';
import {UserServiceService} from "../../services/user-service.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  constructor(private userService: UserServiceService, private router: Router) { }

  ngOnInit() {
  }

  logout() {
    this.userService.setterToken('');
    this.router.navigate(['/dashboard/unavailable']);
  }

  home() {
    this.router.navigate(['/dashboard']);
  }

}
