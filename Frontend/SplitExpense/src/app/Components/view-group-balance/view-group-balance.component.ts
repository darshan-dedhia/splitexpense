import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material/dialog';
import {Report} from '../../classes/report';

@Component({
  selector: 'app-view-group-balance',
  templateUrl: './view-group-balance.component.html',
  styleUrls: ['./view-group-balance.component.scss']
})
export class ViewGroupBalanceComponent implements OnInit {

  constructor(@Inject(MAT_DIALOG_DATA) public data: any) {
  }

  ngOnInit() {
  }

}
