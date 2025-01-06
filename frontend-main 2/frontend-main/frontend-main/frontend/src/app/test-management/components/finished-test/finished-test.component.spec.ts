import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FinishedTestComponent } from './finished-test.component';

describe('FinishedTestComponent', () => {
  let component: FinishedTestComponent;
  let fixture: ComponentFixture<FinishedTestComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FinishedTestComponent]
    });
    fixture = TestBed.createComponent(FinishedTestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
