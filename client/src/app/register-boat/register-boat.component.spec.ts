import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterBoatComponent } from './register-boat.component';

describe('RegisterBoatComponent', () => {
  let component: RegisterBoatComponent;
  let fixture: ComponentFixture<RegisterBoatComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegisterBoatComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegisterBoatComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
