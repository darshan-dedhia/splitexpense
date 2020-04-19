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
  constructor(private activatedRoute: ActivatedRoute,
              private userService: UserServiceService,
              private transactionService: TransactionService,
              private selectedTransaction: SelectedTransactionService,
              private dialog: MatDialog) {
      this.selectedTransaction.emptyTransactions();
  }
  private user: User;

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
      console.log(this.user);
      this.transactionService.fetchFriendsTransaction(this.user.userId).subscribe(data => {
        if (data['statusCode'] === 200) {
          this.selectedTransaction.loadTransactions(data['object']);
          this.dataSource = new ExampleDataSource().connect(this.selectedTransaction.getTransactionList());
        } else {
          console.log(data); //Add small card below
        }
      }, error => {
          console.log(error);
      });
    });
  }

  addExpense() {
    this.selectedTransaction.addNewTransaction([this.user, this.userService.getterUser()]);
    this.expenseAddModify('Add Transaction');
  }

  expenseAddModify(title: string) {

    const diaRef = this.dialog.open(AddModifyTransactionsComponent, {data: {title}, disableClose: true, autoFocus: true});
    diaRef.afterClosed().subscribe(result => {
        if (result === true) {
          console.log(this.selectedTransaction.getTransactionList());
          this.dataSource = new ExampleDataSource().connect(this.selectedTransaction.getTransactionList());
        }
    });
  }

  updateTransaction(transaction: Transaction, event: MouseEvent) {
    console.log(transaction);
    event.stopPropagation();
    this.selectedTransaction.setSelectedTransaction(transaction);
    const diaRef = this.dialog.open(AddModifyTransactionsComponent, {data: {title: 'Modify Transaction'}, disableClose: true, autoFocus: true});
  }

  deleteTransaction(transaction: Transaction, event: MouseEvent) {
    console.log(transaction);
    event.stopPropagation();
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
