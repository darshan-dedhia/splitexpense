import { Injectable } from '@angular/core';
import {HttpHeaders} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class GlobalVariablesService {
    private url = 'http://localhost:8080/finalproject';
  constructor() { }

  getUrl() {
    return this.url;
  }

  getDefaultHttpHeader() {
    return new HttpHeaders().set('Content-Type', 'application/json');
  }
}
