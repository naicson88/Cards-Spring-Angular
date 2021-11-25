import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CardinfoComponent } from './cardinfo.component';

describe('CardinfoComponent', () => {
  let component: CardinfoComponent;
  let fixture: ComponentFixture<CardinfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CardinfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CardinfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
