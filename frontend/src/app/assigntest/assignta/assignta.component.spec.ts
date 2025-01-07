import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AssigntaComponent } from './assignta.component';

describe('AssigntaComponent', () => {
  let component: AssigntaComponent;
  let fixture: ComponentFixture<AssigntaComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AssigntaComponent]
    });
    fixture = TestBed.createComponent(AssigntaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
