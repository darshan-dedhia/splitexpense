import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import {User} from '../../classes/user';
import {UserServiceService} from '../../services/user-service.service';
import { animate, state, style, transition, trigger } from '@angular/animations';
import {DataSource} from '@angular/cdk/collections';
import {Observable, of} from 'rxjs';
import {TransactionService} from '../../services/transaction.service';
import {Transaction} from '../../classes/transaction';
import {MatDialog, MatDialogConfig, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {AddModifyTransactionsComponent} from '../add-modify-transactions/add-modify-transactions.component';
import {SelectedTransactionService} from '../../services/selected-transaction.service';
import {YesNoDialogComponent} from '../../dialogComponent/yes-no-dialog/yes-no-dialog.component';
import {MatSnackBar} from '@angular/material/snack-bar';
import {SettleUpComponent} from '../settle-up/settle-up.component';
import {Report} from "../../classes/report";
import {UserRelatedService} from "../../services/user-related.service";



@Component({
  selector: 'app-friends',
  templateUrl: './friends.component.html',
  styleUrls: ['./friends.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0', visibility: 'hidden' })),
      state('expanded', style({ height: '*', visibility: 'visible' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class FriendsComponent implements OnInit {
  private user: User;
  private report: Report[];
  private totals: Report;
  private friend: Report;
  constructor(private activatedRoute: ActivatedRoute,
              private userService: UserServiceService,
              private transactionService: TransactionService,
              private selectedTransaction: SelectedTransactionService,
              private dialog: MatDialog,
              private snackBar: MatSnackBar,
              private userRelatedService: UserRelatedService) {
      this.selectedTransaction.emptyTransactions();
      if (this.totals === undefined){
      this.totals = new Report();
      this.totals.ownShare = 0;
      this.totals.paid = 0;
      this.totals.user = 0;
      this.totals.id = 0;
      this.totals.percentage = 0;
      this.totals.transaction = 0;
    }
      if (this.friend === undefined){
      this.friend = new Report();
      this.friend.ownShare = 0;
      this.friend.paid = 0;
      this.friend.user = 0;
      this.friend.id = 0;
      this.friend.percentage = 0;
      this.friend.transaction = 0;
    }
  }


  displayedColumns: string[] = ['transactionDate', 'category', 'description', 'payment', 'action'];
  expandedElement: any;
  private dataSource: any;
  css = 'expansion-detail-title';
  isExpansionDetailRow = (i: number, row: any) => row.hasOwnProperty('detailRow');
  ngOnInit() {
    // let id = parseInt(this.activatedRoute.snapshot.paramMap.get('id'));
    // this.userId = id;
    this.activatedRoute.paramMap.subscribe((params: ParamMap) => {
      this.user = new User();
      this.selectedTransaction.emptyTransactions();
      let id = parseInt(params.get('id'));
      this.user = this.userService.getterFriendsList().find(({userId}) => userId === id);
      this.transactionService.fetchFriendsTransaction(this.user.userId).subscribe(data => {
        if (data['statusCode'] === 200) {

          this.selectedTransaction.loadTransactions(data['object']);
          this.selectedTransaction.setIndividualOrGroup(0);
          this.selectedTransaction.setTransactionUserList([this.user, this.userService.getterUser()]);
          this.dataSource = new ExampleDataSource().connect(this.selectedTransaction.getTransactionList());
          this.fetchUpdatedReportData();
        } else {
          console.log(data); //Add small card below
        }
      }, error => {
          console.log(error);
      });
    });
  }

  addExpense() {
    this.selectedTransaction.addNewTransaction([this.user, this.userService.getterUser()], 0);
    this.expenseAddModify('Add Transaction');
  }

  expenseAddModify(title: string) {

    const diaRef = this.dialog.open(AddModifyTransactionsComponent, {data: {title}, disableClose: true, autoFocus: true});
    diaRef.afterClosed().subscribe(result => {
        if (result === true) {
          this.fetchUpdatedReportData();
          this.dataSource = new ExampleDataSource().connect(this.selectedTransaction.getTransactionList());
        }
    });
  }

  updateTransaction(transaction: Transaction, event: MouseEvent) {
    console.log(transaction);
    event.stopPropagation();
    this.selectedTransaction.setSelectedTransaction(transaction);
    const diaRef = this.dialog.open(AddModifyTransactionsComponent, {data: {title: 'Modify Transaction'}, disableClose: true, autoFocus: true});
    diaRef.afterClosed().subscribe(result => {
      this.fetchUpdatedReportData();
      this.dataSource = new ExampleDataSource().connect(this.selectedTransaction.getTransactionList());
    });
  }

  deleteTransaction(transaction: Transaction, event: MouseEvent) {
    console.log(transaction);
    event.stopPropagation();
    const result = this.dialog.open(YesNoDialogComponent, {data: {title: 'Confirm Deletion', content: 'Are you sure you want to delete selected record'}, disableClose: true, autoFocus: true});
    result.afterClosed().subscribe(data => {
      if (data === 'YES') {
        this.transactionService.deleteTransaction(transaction).subscribe(data => {
          if (data['statusCode'] === 200) {
            this.selectedTransaction.removeTransaction(transaction);
            this.fetchUpdatedReportData();
            this.dataSource = new ExampleDataSource().connect(this.selectedTransaction.getTransactionList());
            this.snackBar.open('Deletion Successfull', 'Dismiss', {duration: 2000});
          } else {
            this.snackBar.open('Error occured while Deletion', 'Dismiss', {duration: 3000});
          }
        }, error => {
          this.snackBar.open('Remote Server Error', 'Dismiss', {duration: 3000});
        });
      } else {
        this.snackBar.open('Deletion Aborted', 'Dismiss', {duration: 2000});
      }
    });
  }

  settleUp() {
      console.log(this.selectedTransaction.getTransactionUserList());
      this.dialog.open(SettleUpComponent).afterClosed().subscribe(data => {
        this.fetchUpdatedReportData();
        this.dataSource = new ExampleDataSource().connect(this.selectedTransaction.getTransactionList());
      });

  }

  fetchUpdatedReportData() {
    this.totals = new Report();
    this.friend = new Report();
      this.transactionService.fetchReport(this.user.userId).subscribe(data => {
        if (data['statusCode'] === 200) {
          this.report = data['object'];
          console.log(this.report);
          this.totals = this.report.find((rpt) => rpt.user === this.userService.getterUser().userId);
          this.friend = this.report.find((r) => r.user === this.user.userId);
          console.log(this.totals);
          console.log(this.friend);
        }
        if (this.totals === undefined){
          this.totals = new Report();
          this.totals.ownShare = 0;
          this.totals.paid = 0;
          this.totals.user = 0;
          this.totals.id = 0;
          this.totals.percentage = 0;
          this.totals.transaction = 0;
        }
        if (this.friend === undefined){
          this.friend = new Report();
          this.friend.ownShare = 0;
          this.friend.paid = 0;
          this.friend.user = 0;
          this.friend.id = 0;
          this.friend.percentage = 0;
          this.friend.transaction = 0;
        }

      });
  }

  email() {
    if (this.totals.paid > this.totals.ownShare) {
      let mailer = this.dialog.open(YesNoDialogComponent, {data: {title: 'Confirm Message', content: 'Please confirm to send the mail'}, disableClose: true, autoFocus: true});
      mailer.afterClosed().subscribe(result => {
        if (result === 'YES'){
          let body = `Hi ${this.user.name}, \n Your friend ${this.userService.getterUser().name} has sent reminder for settling ${this.totals.paid - this.totals.ownShare}`;
            this.userRelatedService.sendMail(this.user.email, 'Expense Settlement Reminder', body).subscribe(data=>{
              if (data['statusCode'] === 200){
                this.snackBar.open('Email message sent successfully', 'Dismiss', {duration: 2000})
              } else {
                this.snackBar.open('Email service is down', 'Dismiss', {duration: 3000})
              }
            }, error => {
              this.snackBar.open('Email server responses with error', 'Dismiss', {duration: 3000})
            });
        }
      });
    } else {
      this.snackBar.open('No recievables. Hence Mail wont be sent', 'Dismiss', {duration: 3000});
    }
  }


}

export class ExampleDataSource extends DataSource<any> {
  /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(ELEMENT_DATA): Observable<Element[]> {
    const rows = [];
    ELEMENT_DATA.forEach(element => rows.push(element, { detailRow: true, element }));
    console.log(rows);
    return of(rows);
  }

  disconnect() { }
}
