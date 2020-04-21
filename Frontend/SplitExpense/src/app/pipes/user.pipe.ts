import { Pipe, PipeTransform } from '@angular/core';
import {UserServiceService} from '../services/user-service.service';
import {User} from "../classes/user";

@Pipe({
  name: 'user',
  pure: false
})
export class UserPipe implements PipeTransform {

  private user: User;

  constructor(private userService: UserServiceService) {
    this.user = userService.getterUser();
  }
  transform(value: any, ...args: any[]): any {
    return  this.userService.getterFriendsList().find((u) => u.userId === value).name;

  }

}
