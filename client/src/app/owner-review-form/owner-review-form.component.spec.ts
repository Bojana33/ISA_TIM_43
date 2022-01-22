import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OwnerReviewFormComponent } from './owner-review-form.component';

describe('OwnerReviewFormComponent', () => {
  let component: OwnerReviewFormComponent;
  let fixture: ComponentFixture<OwnerReviewFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OwnerReviewFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OwnerReviewFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
