import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CottagesUserComponent } from './cottages-user.component';

describe('CottagesComponent', () => {
  let component: CottagesUserComponent;
  let fixture: ComponentFixture<CottagesUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CottagesUserComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CottagesUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
