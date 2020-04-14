import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';

@Component({
  selector: 'app-friends',
  templateUrl: './friends.component.html',
  styleUrls: ['./friends.component.scss']
})
export class FriendsComponent implements OnInit {
  private userId: number;
  constructor(private activatedRoute: ActivatedRoute) {

  }

  ngOnInit() {
    // let id = parseInt(this.activatedRoute.snapshot.paramMap.get('id'));
    // this.userId = id;
    this.activatedRoute.paramMap.subscribe((params: ParamMap) => {
      let id = parseInt(params.get('id'));
      this.userId = id;
    });
    console.log(this.userId);
  }

}
