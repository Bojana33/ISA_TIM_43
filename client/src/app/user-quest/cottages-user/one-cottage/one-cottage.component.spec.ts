import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OneCottageComponent } from './one-cottage.component';

describe('OneCottageComponent', () => {
  let component: OneCottageComponent;
  let fixture: ComponentFixture<OneCottageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OneCottageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OneCottageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
