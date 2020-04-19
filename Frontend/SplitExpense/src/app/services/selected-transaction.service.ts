import { Injectable } from '@angular/core';
import {Transaction} from '../classes/transaction';
import {User} from '../classes/user';
import {TransactionDetails} from '../classes/transaction-details';


@Injectable({
  providedIn: 'root'
})
export class SelectedTransactionService {
  private transactionList: Transaction[];
  private transaction: Transaction;
  private users: User[];
  private equalSelectedUser: User[];
  public backUpTransaction: Transaction;
  private operation: string;

  constructor() { }

  emptyTransactions() {
    this.transactionList = new Array<Transaction>();
    this.transaction = new Transaction();
    this.backUpTransaction = new Transaction();
    this.equalSelectedUser = new Array<User>();
    this.operation = '';
  }

  loadTransactions(transactions: Transaction[]) {
    this.emptyTransactions();
    this.transactionList = transactions;
    this.operation = '';
  }

  setSelectedTransaction(transaction: Transaction) {
    this.operation = 'UPDATE';
    this.transaction = new Transaction();
    this.backUpTransaction = new Transaction();
    this.equalSelectedUser = new Array<User>();
    this.users = new Array<User>();
    this.transaction = transaction;
    Object.assign(this.backUpTransaction, this.transaction);
    if (this.transaction.splitMethod === 0) {
      this.transaction.transactionDetails.forEach(td => {
        this.users.push(td.user);
        if (td.ownShare > 0) {
          this.equalSelectedUser.push(td.user);
        }
      });
    }
  }

  addNewTransaction(users: User[]) {
    this.operation = 'ADD';
    this.transaction = new Transaction();
    this.backUpTransaction = new Transaction();
    this.transaction.totalAmount = 0.0;
    this.users = users;
    this.equalSelectedUser = users;
    this.transaction.paymentIndividualOrGroupId = 0;
    this.transaction.category = '';
    this.transaction.description = '';
    this.transaction.splitMethod = 0;
    this.transaction.transactionDate = new Date();
    this.users.forEach((user) => {
      let transactionDetail = new TransactionDetails();
      transactionDetail.user = user;
      transactionDetail.ownShare = 0.0;
      transactionDetail.paid = 0.0;
      transactionDetail.percentage = 0.0;
      this.transaction.transactionDetails.push(transactionDetail);
    });
  }

  getTransactionList() {
    return this.transactionList;
  }

  getTransaction() {
    return this.transaction;
  }

  getUsers() {
    return this.users;
  }

  getNoOfUsers() {
    return this.users.length;
  }

  addEqualSelectedUser(user: User) {
    this.equalSelectedUser.push(user);
  }

  removeEqualSelectedUser(user: User) {
    const index = this.equalSelectedUser.indexOf(user);
    if (index !== -1) { this.equalSelectedUser.splice(index, 1); }
  }

  getEqualSelectedUser() {
    return this.equalSelectedUser;
  }

  equalSelectedUserContains(user: User) {
    return (this.equalSelectedUser.includes(user));
  }

  replaceBackUpTransaction() {
    let index = this.getTransactionList().indexOf(this.getTransaction());
    if (index !== -1) {
      console.log('In here -1');
      this.getTransactionList()[index] = this.backUpTransaction;
    }
    console.log(this.getTransactionList());
  }

  getOperation() {
    return this.operation;
  }

  setOperation(operation: string) {
    this.operation = operation;
  }

  removeTransaction(transaction: Transaction) {
    const index = this.transactionList.indexOf(transaction);
    if (index !== -1) {
      this.transactionList.splice(index, 1);
    }
  }

}

