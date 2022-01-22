import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InstructorAvailabilityFormComponent } from './instructor-availability-form.component';

describe('InstructorAvailabilityFormComponent', () => {
  let component: InstructorAvailabilityFormComponent;
  let fixture: ComponentFixture<InstructorAvailabilityFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InstructorAvailabilityFormComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InstructorAvailabilityFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
