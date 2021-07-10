import { TestBed } from '@angular/core/testing';

import { CardServiceService } from './card-service.service';

describe('CardServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CardServiceService = TestBed.get(CardServiceService);
    expect(service).toBeTruthy();
  });
});
