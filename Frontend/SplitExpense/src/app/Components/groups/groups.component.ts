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
import {Group} from "../../classes/group";
import {SettleUpComponent} from "../settle-up/settle-up.component";
import {AddModifyGroupComponent} from "../add-modify-group/add-modify-group.component";
import {Report} from "../../classes/report";
import {ViewGroupBalanceComponent} from "../view-group-balance/view-group-balance.component";

@Component({
  selector: 'app-groups',
  templateUrl: './groups.component.html',
  styleUrls: ['./groups.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0', visibility: 'hidden' })),
      state('expanded', style({ height: '*', visibility: 'visible' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ]
})
export class GroupsComponent implements OnInit {
  private groupId: number;
  constructor(private activatedRoute: ActivatedRoute,
              private userService: UserServiceService,
              private transactionService: TransactionService,
              private selectedTransaction: SelectedTransactionService,
              private dialog: MatDialog,
              private snackBar: MatSnackBar) {
    this.selectedTransaction.emptyTransactions();
  }
  //private user: User;
  private group: Group;
  displayedColumns: string[] = ['transactionDate', 'category', 'description', 'payment', 'action'];
  expandedElement: any;
  private dataSource: any;
  css = 'expansion-detail-title';
  isExpansionDetailRow = (i: number, row: any) => row.hasOwnProperty('detailRow');

  ngOnInit() {
    // let id = parseInt(this.activatedRoute.snapshot.paramMap.get('id'));
    // this.userId = id;
    this.activatedRoute.paramMap.subscribe((params: ParamMap) => {
      // this.user = new User();
      this.selectedTransaction.emptyTransactions();
      const id = parseInt(params.get('id'));
      if (id !== null || id !== 0) {
      this.transactionService.fetchGroupTransactions(id).subscribe(data => {
        if (data['statusCode'] === 200) {
          this.group = data['group'];
          this.group.userList = data['users'];
          console.log(this.group.groupId);
          this.selectedTransaction.setIndividualOrGroup(this.group.groupId);
          this.selectedTransaction.setTransactionUserList(this.group.userList);
          this.selectedTransaction.loadTransactions(data['transactions']);
          this.dataSource = new ExampleDataSource().connect(this.selectedTransaction.getTransactionList());
        } else {
          console.log(data); // Add small card below
        }
      }, error => {
        console.log(error);
      });}
    });
  }

  addExpense() {
    this.selectedTransaction.addNewTransaction(this.group.userList, this.group.groupId);
    this.expenseAddModify('Add Transaction');
  }

  expenseAddModify(title: string) {

    const diaRef = this.dialog.open(AddModifyTransactionsComponent, {data: {title}, disableClose: true, autoFocus: true});
    diaRef.afterClosed().subscribe(result => {
      if (result === true) {
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
      this.dataSource = new ExampleDataSource().connect(this.selectedTransaction.getTransactionList());
    });

  }

  modifyGroup() {
    this.dialog.open(AddModifyGroupComponent, {data: {friendList: this.userService.getterFriendsList(), group: this.group, title: 'Update Group', call: 'UPDATE'}, disableClose: true, autoFocus: true});
  }
  viewBalances() {
    this.transactionService.fetchGroupBalance(this.group.groupId).subscribe(data => {
      if (data['statusCode'] === 200) {
        let report = data['object'] as Report[];
        let res = [];
        if (report.length > 0) {
          report.forEach((rpt) => {
            let user = this.group.userList.find((usr) => usr.userId === rpt.user);
            let str: string;
            str = user.name;
            if (rpt.paid >= rpt.ownShare) {
              str += ' gets back $' + `${rpt.paid - rpt.ownShare}`;
            } else if (rpt.paid < rpt.ownShare) {
              str += ' has to pay $' + `${rpt.ownShare - rpt.paid}`;
            }
            console.log(str);
            res.push(str);
          });
          console.log('Final op');
          console.log(res);
          this.dialog.open(ViewGroupBalanceComponent, {data: {result: res}});
        } else {
          this.snackBar.open('No records to be displayed', 'Dismiss', {duration: 2000});
        }
      } else {
        this.snackBar.open('View balance failed', 'Dismiss', {duration: 2000});
      }
    }, error => {
      this.snackBar.open('Error while viewing balance', 'Dismiss', {duration: 2000});
    });
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
