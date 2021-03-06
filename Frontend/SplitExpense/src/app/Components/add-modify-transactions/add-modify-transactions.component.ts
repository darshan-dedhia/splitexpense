import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {Transaction} from '../../classes/transaction';
import { SelectedTransactionService } from '../../services/selected-transaction.service';
import {TransactionDetails} from '../../classes/transaction-details';
import {TransactionService} from '../../services/transaction.service';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'app-add-modify-transactions',
  templateUrl: './add-modify-transactions.component.html',
  styleUrls: ['./add-modify-transactions.component.scss']
})
export class AddModifyTransactionsComponent implements OnInit {
  private date = new Date((new Date()));
  private rateControl;
  private categories = ['General', 'Rent', 'Mortgage', 'Household supplies', 'Services', 'Medical Expenses', 'Transport', 'Heat', 'Electricity'];
  constructor(@Inject(MAT_DIALOG_DATA) public data: any,
              private selTransactions: SelectedTransactionService,
              private transactionService: TransactionService,
              private dialogRef: MatDialogRef<AddModifyTransactionsComponent>,
              private snackbar: MatSnackBar) {
    console.log(this.selTransactions);
  }

  ngOnInit() {
  }

  equalCheckBoxChange(input, td: TransactionDetails) {
      if (input.checked) {
        this.selTransactions.addEqualSelectedUser(td.user);
        this.recalculateShare(this.selTransactions.getTransaction().splitMethod);  //Split here
      } else {
        this.selTransactions.removeEqualSelectedUser(td.user);
        this.recalculateShare(this.selTransactions.getTransaction().splitMethod); //Split here
      }
  }

  matTabChange(event) {
      this.selTransactions.getTransaction().transactionDetails.forEach(td => {
        td.ownShare = 0;
      });
      this.recalculateShare(this.selTransactions.getTransaction().splitMethod);
  }
  inputTotalAmountChange(event) {
    console.log(event);
    this.recalculateShare(this.selTransactions.getTransaction().splitMethod);
  }

  inputPercentChange(event) {
    this.recalculateShare(this.selTransactions.getTransaction().splitMethod);
  }

  recalculateShare(selectedTabIndex: number) {
    console.log(selectedTabIndex);
    if (selectedTabIndex === 0) {
      const users = this.selTransactions.getEqualSelectedUser();
      this.selTransactions.getTransaction().transactionDetails.forEach(td => {
        if (users.includes(td.user) && this.selTransactions.getEqualSelectedUser().length > 0) {
          td.ownShare = this.selTransactions.getTransaction().totalAmount / this.selTransactions.getEqualSelectedUser().length;
        } else {
          td.ownShare = 0;
        }
        console.log(td);
      });
    } else if (selectedTabIndex === 1) {
      this.selTransactions.getTransaction().transactionDetails.forEach(td => {
        td.ownShare = ((td.percentage) / 100) * this.selTransactions.getTransaction().totalAmount;
        console.log(td);
      });
    }
  }
  formSubmit() {
    const result = this.validatePayment();
    if (result === 'SUCCESS') {
      this.transactionService.addTransaction(this.selTransactions.getTransaction()).subscribe(data => {
        if (data['statusCode'] === 200) {
          let transaction = data['object'];
          if (this.selTransactions.getOperation() === 'ADD') {
            this.selTransactions.getTransactionList().push(transaction);
          }
          this.snackbar.open('Transaction save successfully', 'Dismiss', {duration: 2000});
          this.selTransactions.setSelectedTransaction(new Transaction());
          this.closeDialog(true);
        } else {
          this.snackbar.open('Error occured while saving transaction', 'Dismiss', {duration: 3000});
        }
      }, error => {
        console.log(error);
      });
    } else if (result === 'ALL_AMOUNT_ZERO') {
      this.snackbar.open('All amounts are zero', 'Dismiss', {duration: 3000});
    } else if (result === 'TOTAL_CREDITS_DEBIT_DONT_MATCH') {
      this.snackbar.open('Total spendings dont match total earnings', 'Dismiss', {duration: 3000});
    }
  }

  validatePayment() {
    if (!this.validateInputs()) {return 'FAILURE'; }
    const transaction = this.selTransactions.getTransaction();
    console.log(transaction);
    let totalAmount: number;
    totalAmount = this.selTransactions.getTransaction().totalAmount;
    let totalPaidAmount: number;
    let totalShare: number;
    totalPaidAmount = 0; totalShare= 0;
    transaction.transactionDetails.forEach(td => {
      totalPaidAmount += td.paid;
      totalShare += td.ownShare;
    });
    if (totalAmount === 0 && totalPaidAmount === 0 && totalShare === 0) {
      return 'ALL_AMOUNT_ZERO';
    }
    if ( totalAmount !== totalPaidAmount || totalPaidAmount !== totalShare) {
      return 'TOTAL_CREDITS_DEBIT_DONT_MATCH';
    }
    return 'SUCCESS';
  }

  validateInputs() {
    const transaction = this.selTransactions.getTransaction();
    if ( transaction.paymentIndividualOrGroupId === null || transaction.paymentIndividualOrGroupId === undefined) {
      this.snackbar.open('Inernal group ID error occured', 'Dismiss', {duration: 2000});
      return false;
    }

    if (transaction.description === '' || transaction.description.trim().length === 0 || transaction.description === null || transaction.description === undefined) {
      this.snackbar.open('Description is empty', 'Dismiss', {duration: 2000});
      return false;
    }

    if (transaction.category === '' || transaction.category.trim().length === 0 || transaction.category === null || transaction.category === undefined) {
      this.snackbar.open('Category cannot be empty', 'Dismiss', {duration: 2000});
      return false;
    }

    if (transaction.totalAmount < 0 || transaction.description.trim().length === 0 || transaction.description === null || transaction.description === undefined) {
      this.snackbar.open('Total amount should be greater than zero', 'Dismiss', {duration: 2000});
      return false;
    }

    for(let i = 0; i < transaction.transactionDetails.length; i++){

      if (transaction.transactionDetails[i].percentage < 0) {
        this.snackbar.open('Percentage should be from 0-100', 'Dismiss', {duration: 2000});
        return false;
      }

      if (transaction.transactionDetails[i].ownShare < 0 || transaction.transactionDetails[i].paid < 0) {
        this.snackbar.open('Amount should be more than 0', 'Dismiss', {duration: 2000});
        return false;
      }
    }

    return true;
  }

  closeDialog(result) {
    this.dialogRef.close(result);
  }

  cancel(event: MouseEvent) {
    event.stopImmediatePropagation();
    this.selTransactions.replaceBackUpTransaction();
    this.closeDialog('CANCEL');
  }

  equalCheckBoxValue(td: TransactionDetails) {
    return this.selTransactions.getEqualSelectedUser().find((user) => user === td.user);
  }

}
