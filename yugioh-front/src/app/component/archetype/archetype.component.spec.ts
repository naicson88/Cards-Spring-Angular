import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArchetypeComponent } from './archetype.component';

describe('ArchetypeComponent', () => {
  let component: ArchetypeComponent;
  let fixture: ComponentFixture<ArchetypeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArchetypeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArchetypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
