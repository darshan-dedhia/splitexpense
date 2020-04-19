import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddModifyTransactionsComponent } from './add-modify-transactions.component';

describe('AddModifyTransactionsComponent', () => {
  let component: AddModifyTransactionsComponent;
  let fixture: ComponentFixture<AddModifyTransactionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddModifyTransactionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddModifyTransactionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
