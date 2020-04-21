import { Component, OnInit } from '@angular/core';
import {UserServiceService} from "../../services/user-service.service";
import {Report} from "../../classes/report";
import {TransactionService} from "../../services/transaction.service";

@Component({
  selector: 'app-summary',
  templateUrl: './summary.component.html',
  styleUrls: ['./summary.component.scss']
})
export class SummaryComponent implements OnInit {
  private totals: Report;
  private allMembersRpt: Report[];
  private catTot: string[];
  constructor(private userService: UserServiceService, private transactServices: TransactionService) {
    this.transactServices.fetchSelfTotals().subscribe(data => {
      if (data['statusCode'] === 200) {
        this.totals = data['object'];
      } else {
        this.totals = new Report();
        this.totals.user = 0;
        this.totals.ownShare = 0;
        this.totals.paid = 0;
        this.totals.transaction = 0;
        this.totals.percentage = 0;
        this.totals.id = 0;
      }
    });
    this.transactServices.fetchReport(this.userService.getterUser().userId).subscribe(data => {
      if (data['statusCode'] === 200) {
        this.allMembersRpt = data['object'];
        const index = this.allMembersRpt.findIndex((u) => u.user === this.userService.getterUser().userId);
        if (index !== -1) { this.allMembersRpt.splice(index, 1); }
      } else {
        this.allMembersRpt = new Array<Report>();
      }
    }, error => {
      this.allMembersRpt = new Array<Report>();
    });

    this.transactServices.fetchCategorizedTotal().subscribe(data => {
      if (data['statusCode'] === 200) {
        this.catTot = data['object'];
      } else {
        this.catTot = new Array<string>();
      }
    });

  }

  ngOnInit() {
  }

}
