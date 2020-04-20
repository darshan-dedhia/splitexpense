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
      let arg1 =  args[0];
      let category = arg1.category;
      if (category !== 'SETTLEMENT') {
        if (value.ownShare > value.paid) {
          return 'You owe: ' + Math.abs(value.paid - value.ownShare);
        } else if (value.ownShare === value.paid) {
          return 'All set';
        } else {
          return 'You Lent: ' + (value.paid - value.ownShare);
        }
      } else {
        if (value.paid > 0) { return 'You paid: ' + value.paid; }
        if (value.ownShare > 0) { return 'You recieved: ' + value.ownShare; }
      }
    }
  }

}
