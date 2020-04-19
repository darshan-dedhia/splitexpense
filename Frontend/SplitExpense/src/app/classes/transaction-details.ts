import {User} from './user';

export class TransactionDetails {
  public id: number;
  public user: User;
  public paid: number;
  public ownShare: number;
  public percentage: number;
}
