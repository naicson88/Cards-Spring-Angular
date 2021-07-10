import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArchetypeDetailsComponent } from './archetype-details.component';

describe('ArchetypeDetailsComponent', () => {
  let component: ArchetypeDetailsComponent;
  let fixture: ComponentFixture<ArchetypeDetailsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArchetypeDetailsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArchetypeDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
