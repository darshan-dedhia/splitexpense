import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {User} from '../classes/user';
import {GlobalVariablesService} from './global-variables.service';
import {Transaction} from '../classes/transaction';

@Injectable({
  providedIn: 'root'
})
export class TransactionService {

  constructor(private http: HttpClient, private global: GlobalVariablesService) { }

  fetchFriendsTransaction(friendUserId: number) {
    // tslint:disable-next-line:max-line-length
    return this.http.get(this.global.getUrl() + `/transactions/fetchFriendTransactions/${friendUserId}`, {headers: this.global.getDefaultHttpHeader()});
  }

  addTransaction(transaction: Transaction) {
    //console.log(transaction);
    return this.http.post(this.global.getUrl() + '/transactions/addTransaction', transaction, {headers: this.global.getDefaultHttpHeader()});
  }

  deleteTransaction(transaction: Transaction) {
    return this.http.post(this.global.getUrl() + `/transactions/deleteTransaction/${transaction.transactionId}`, transaction,{headers: this.global.getDefaultHttpHeader()});
  }

  fetchGroupTransactions(groupId: number) {
    return this.http.get(this.global.getUrl() + `/transactions/groups/${groupId}`, {headers: this.global.getDefaultHttpHeader()});
  }

  fetchReport(friendUserId: number) {
    return this.http.get(this.global.getUrl() + `/transactions/friends/sum/${friendUserId}`, {headers: this.global.getDefaultHttpHeader()});
  }

  fetchGroupBalance(groupId: number){
    return this.http.get(this.global.getUrl() + `/transactions/groups/sum/${groupId}`, {headers: this.global.getDefaultHttpHeader()});
  }

  fetchSelfTotals() {
    return this.http.get(this.global.getUrl() + '/transactions/self/totals', {headers: this.global.getDefaultHttpHeader()});
  }

  fetchCategorizedTotal() {
    return this.http.get(this.global.getUrl() + '/transactions/self/catorisedtotals', {headers: this.global.getDefaultHttpHeader()});
  }

}
