import { TestBed } from '@angular/core/testing';

import { SelectedTransactionService } from './selected-transaction.service';

describe('SelectedTransactionService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: SelectedTransactionService = TestBed.get(SelectedTransactionService);
    expect(service).toBeTruthy();
  });
});
