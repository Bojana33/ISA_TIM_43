import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FastReservationComponent } from './fast-reservation.component';

describe('FastReservationComponent', () => {
  let component: FastReservationComponent;
  let fixture: ComponentFixture<FastReservationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FastReservationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FastReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
