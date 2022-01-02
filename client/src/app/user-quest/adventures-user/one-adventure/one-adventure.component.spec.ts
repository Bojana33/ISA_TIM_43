import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OneAdventureComponent } from './one-adventure.component';

describe('OneAdventureComponent', () => {
  let component: OneAdventureComponent;
  let fixture: ComponentFixture<OneAdventureComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OneAdventureComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(OneAdventureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
