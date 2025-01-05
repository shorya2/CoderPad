import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AttemptedTestComponent } from './attempted-test.component';

describe('AttemptedTestComponent', () => {
  let component: AttemptedTestComponent;
  let fixture: ComponentFixture<AttemptedTestComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AttemptedTestComponent]
    });
    fixture = TestBed.createComponent(AttemptedTestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
