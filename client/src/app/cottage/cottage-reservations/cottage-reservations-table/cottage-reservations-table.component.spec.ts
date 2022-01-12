import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CottageReservationsTableComponent } from './cottage-reservations-table.component';

describe('CottageReservationsTableComponent', () => {
  let component: CottageReservationsTableComponent;
  let fixture: ComponentFixture<CottageReservationsTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CottageReservationsTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CottageReservationsTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
