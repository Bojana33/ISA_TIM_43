import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoatsUserComponent } from './boats-user.component';

describe('BoatsComponent', () => {
  let component: BoatsUserComponent;
  let fixture: ComponentFixture<BoatsUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BoatsUserComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BoatsUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
