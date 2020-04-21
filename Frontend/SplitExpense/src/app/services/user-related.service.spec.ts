import { TestBed } from '@angular/core/testing';

import { UserRelatedService } from './user-related.service';

describe('UserRelatedService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: UserRelatedService = TestBed.get(UserRelatedService);
    expect(service).toBeTruthy();
  });
});
