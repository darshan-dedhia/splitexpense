import { Injectable } from '@angular/core';
import { CanActivateChild, Router} from '@angular/router';
import {UserServiceService} from '../services/user-service.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardGuard implements CanActivateChild {
  constructor(private userService: UserServiceService,
              private router: Router) { }

  canActivateChild(): boolean {
    if (this.userService.userLoggedIn()) {
      return true;
    } else {
      this.router.navigate(['/']);
      return false;
    }
  }

}
