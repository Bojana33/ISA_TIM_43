import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CalendarView } from 'angular-calendar';

@Component({
  // tslint:disable-next-line:component-selector
  selector: 'mwl-demo-utils-calendar-header',
  templateUrl: './calendar-header.component.html',
  styleUrls: ['./calendar-header.component.css']
})
export class CalendarHeaderComponent {
  // @ts-ignore
  @Input() view: CalendarView;

  // @ts-ignore
  @Input() viewDate: Date;

  @Input() locale = 'en';

  @Output() viewChange = new EventEmitter<CalendarView>();

  @Output() viewDateChange = new EventEmitter<Date>();

  CalendarView = CalendarView;
}
