export class User {

  public userId?: number;
  public userName: string;
  public password: string;
  public name: string;
  public email: string;
  public friends?: User[];
  public friendsOf?: User[];
  public groupList?: string[];

}
