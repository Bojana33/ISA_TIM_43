import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OneBoatUserComponent } from './one-boat-user.component';

describe('OneBoatUserComponent', () => {
  let component: OneBoatUserComponent;
  let fixture: ComponentFixture<OneBoatUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OneBoatUserComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OneBoatUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
