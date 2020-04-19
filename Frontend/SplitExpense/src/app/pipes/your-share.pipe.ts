import { Pipe, PipeTransform } from '@angular/core';
import {UserServiceService} from "../services/user-service.service";
import {User} from '../classes/user';

@Pipe({
  name: 'yourShare',
  pure: false
})
export class YourSharePipe implements PipeTransform {

  private user: User;

  constructor(private userService: UserServiceService) {
    this.user = userService.getterUser();
  }

  transform(value: any, ...args: any[]): any {
    if (value.user.userId === this.user.userId) {
      if (value.ownShare > value.paid) {
        return 'You owe: ' +  Math.abs(value.paid - value.ownShare);
      } else if (value.ownShare === value.paid) {
        return 'All set';
      } else {
        return 'You Lent: ' + (value.paid - value.ownShare);
      }
    }
  }

}
