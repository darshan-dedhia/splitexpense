import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {GlobalVariablesService} from './global-variables.service';
import {Group} from '../classes/group';

@Injectable({
  providedIn: 'root'
})
export class GroupService {

  constructor(private http: HttpClient, private global: GlobalVariablesService) { }

  addUpdateGroup(group: Group) {

    return this.http.post(this.global.getUrl() + '/groups/addUpdate', group, {headers: this.global.getDefaultHttpHeader()});
  }

}
