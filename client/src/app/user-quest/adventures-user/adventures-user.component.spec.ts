import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdventuresUserComponent } from './adventures-user.component';

describe('AdventuresUserComponent', () => {
  let component: AdventuresUserComponent;
  let fixture: ComponentFixture<AdventuresUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdventuresUserComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdventuresUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
