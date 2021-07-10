import { TestBed } from '@angular/core/testing';

import { AchetypeService } from './achetype.service';

describe('AchetypeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AchetypeService = TestBed.get(AchetypeService);
    expect(service).toBeTruthy();
  });
});
