import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FastReservationDialogComponent } from './fast-reservation-dialog.component';

describe('FastReservationDialogComponent', () => {
  let component: FastReservationDialogComponent;
  let fixture: ComponentFixture<FastReservationDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FastReservationDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FastReservationDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
