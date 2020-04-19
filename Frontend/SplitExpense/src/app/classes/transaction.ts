import {TransactionDetails} from './transaction-details';

export class Transaction {
  public transactionId?: number;
  public paymentIndividualOrGroupId: number;
  public description: string;
  public category: string;
  public transactionDate: Date;
  public totalAmount: number;
  public splitMethod: number = 0;
  public transactionDetails: TransactionDetails[];
  constructor() {
    this.transactionDetails = new Array<TransactionDetails>();
  }
}
