import { Component, OnInit } from '@angular/core';
import {SelectedTransactionService} from '../../services/selected-transaction.service';
import {TransactionService} from '../../services/transaction.service';
import {User} from '../../classes/user';
import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import {TransactionDetails} from "../../classes/transaction-details";
import {Transaction} from "../../classes/transaction";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-settle-up',
  templateUrl: './settle-up.component.html',
  styleUrls: ['./settle-up.component.scss']
})
export class SettleUpComponent implements OnInit {
  private paidByUserId: any;
  private paidToUserId: any;
  private amount: number;
  private paidByUsr: User;

  constructor(private selectedTransaction: SelectedTransactionService,
              private transactionService: TransactionService,
              private snackBar: MatSnackBar,
              private dialogRef: MatDialogRef<SettleUpComponent>) { }

  ngOnInit() {
  }


  settleSubmission() {
    let result = this.validateSettlement();
    console.log(result);
    if (result === true) {
      let paidByUser = this.selectedTransaction.getTransactionUserList().find((user) => user.userId == this.paidByUserId);
      let paidToUser = this.selectedTransaction.getTransactionUserList().find((user) => user.userId == this.paidToUserId);
      let amount = this.amount;
      const transaction = this.writeTransaction(paidByUser, paidToUser, amount, this.selectedTransaction.getIndividualOrGroup());
      this.transactionService.addTransaction(transaction).subscribe(data => {
        if (data['statusCode'] === 200) {
          const transact = data['object'];
          this.selectedTransaction.getTransactionList().push(transact);
          this.snackBar.open('Transaction save successfully', 'Dismiss', {duration: 2000});
          this.selectedTransaction.setSelectedTransaction(new Transaction());
          this.closeDialog(true);
        } else {
          this.snackBar.open('Error occured while saving transaction', 'Dismiss', {duration: 3000});
        }
      }, error => {
        console.log(error);
      });
    } else {
        this.snackBar.open(result, 'Dismiss', {duration: 3000});
    }
  }

  validateSettlement() {
      if (this.paidToUserId === this.paidByUserId) {
        return 'Paid by and Paid to cannot be same';
      }

      if (this.amount <=  0) {
        return 'Amount has to be more than zero';
      }

      return true;
  }

  writeTransaction(paidByUser: User, paidToUser: User, amount: number, individualGroupId: number) {
    let td = new TransactionDetails();
    td.user = paidByUser;
    td.ownShare = 0.0;
    td.paid = amount;
    td.percentage = 0;

    let td1 = new TransactionDetails();
    td1.user = paidToUser;
    td1.paid = 0;
    td1.ownShare = amount;
    td1.percentage = 0;

    let t = new Transaction();
    t.description = 'SETTLEMENT';
    t.splitMethod = 0;
    t.totalAmount = amount;
    t.transactionDate = new Date();
    t.category = 'SETTLEMENT';
    t.paymentIndividualOrGroupId = individualGroupId;
    t.transactionDetails.push(td);
    t.transactionDetails.push(td1);
    return t;
  }

  closeDialog(result) {
    this.dialogRef.close(result);
  }

}
