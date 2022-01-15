import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InstructorCalendarComponent } from './instructor-calendar.component';

describe('InstructorCalendarComponent', () => {
  let component: InstructorCalendarComponent;
  let fixture: ComponentFixture<InstructorCalendarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ InstructorCalendarComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(InstructorCalendarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
