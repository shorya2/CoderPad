import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssignedcComponent } from './assignedc.component';

describe('AssignedcComponent', () => {
  let component: AssignedcComponent;
  let fixture: ComponentFixture<AssignedcComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AssignedcComponent]
    });
    fixture = TestBed.createComponent(AssignedcComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
