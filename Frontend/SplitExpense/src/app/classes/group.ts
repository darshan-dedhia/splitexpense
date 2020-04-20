import {User} from './user';

export class Group {
  public groupId?: number;
  public groupName: string;
  public users: User[];

  constructor() {
    this.users = new Array<User>();
  }

}
