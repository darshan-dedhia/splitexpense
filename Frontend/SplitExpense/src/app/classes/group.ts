import {User} from './user';

export class Group {
  public groupId?: number;
  public groupName: string;
  public userList: User[];

  constructor(user: User) {
    this.userList = new Array<User>();
    this.userList.push(user);
    this.groupName = '';
  }

}
