import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FutureReservationsComponent } from './future-reservations.component';

describe('FutureReservationsComponent', () => {
  let component: FutureReservationsComponent;
  let fixture: ComponentFixture<FutureReservationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FutureReservationsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FutureReservationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
