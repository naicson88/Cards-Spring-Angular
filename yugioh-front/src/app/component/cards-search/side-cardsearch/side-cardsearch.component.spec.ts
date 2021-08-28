import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SideCardsearchComponent } from './side-cardsearch.component';

describe('SideCardsearchComponent', () => {
  let component: SideCardsearchComponent;
  let fixture: ComponentFixture<SideCardsearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SideCardsearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SideCardsearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
