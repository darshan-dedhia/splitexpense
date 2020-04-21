import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewGroupBalanceComponent } from './view-group-balance.component';

describe('ViewGroupBalanceComponent', () => {
  let component: ViewGroupBalanceComponent;
  let fixture: ComponentFixture<ViewGroupBalanceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewGroupBalanceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewGroupBalanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
