import {ChangeDetectionStrategy, Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {Subject} from 'rxjs';
import {CalendarEvent, CalendarEventTimesChangedEvent, CalendarView} from 'angular-calendar';
import {colors} from '../utils/colors';
import {DatePipe} from '@angular/common';

import {isSameDay} from 'date-fns';
import {ConfigService} from '../service/config.service';
import {ReservationService} from '../service/reservation.service';
import {ReservationDTO} from '../model/reservation-dto.model';
import {ReservationStatus} from '../enum/ReservationStatus';
import {Moment} from 'moment';


@Component({
  selector: 'app-calendar',
  changeDetection: ChangeDetectionStrategy.OnPush,
  styles: [
    `
      .invalid-position .cal-event {
        background-color: #ad2121 !important;
        color: #fff;
      }
    `,
  ],
  encapsulation: ViewEncapsulation.None,
  templateUrl: './calendar.component.html',
  styleUrls: ['./calendar.component.css']
})
export class CalendarComponent implements OnInit{
  view: CalendarView = CalendarView.Week;
  @Input() entityId: any;


  constructor(private configService: ConfigService,
              private reservationService: ReservationService) {}

  viewDate: Date = new Date();
  refresh = new Subject<void>();
  events: CalendarEvent[] = [];
  ngOnInit(): void {
    this.getReservationsForEntity(this.entityId);
  }
  getReservationsForEntity(entityId: number): void{

    this.reservationService.getReservationsForEntity(entityId).subscribe(
      (modelData: ReservationDTO[]) => {
        modelData.forEach((item) => {
          let eventColor = colors.green;
          if (item.reservationStatus.toString() === 'RESERVED'){
            eventColor = colors.red;
          }
          this.events.push({
            id: item.reservedPeriod.id,
            // @ts-ignore
            // tslint:disable-next-line:max-line-length
            start: new Date((item.reservedPeriod.startDate)[0], (item.reservedPeriod.startDate)[1] - 1, (item.reservedPeriod.startDate)[2], (item.reservedPeriod.startDate)[3], (item.reservedPeriod.startDate)[4], (item.reservedPeriod.startDate)[5]),
            // @ts-ignore
            // tslint:disable-next-line:max-line-length
            end: new Date((item.reservedPeriod.endDate)[0], (item.reservedPeriod.endDate)[1] - 1, (item.reservedPeriod.endDate)[2], (item.reservedPeriod.endDate)[3], (item.reservedPeriod.endDate)[4], (item.reservedPeriod.endDate)[5]),
            title: item.reservationStatus.toString() + ' termin',
            color: eventColor,
            draggable: true
          });
        });
        this.refresh.next();
      });
  }

  validateEventTimesChanged = (
    { event, newStart, newEnd, allDay }: CalendarEventTimesChangedEvent,
    addCssClass = true
  ) => {
    if (event.allDay) {
      return true;
    }
    console.log(newStart, newEnd);
    delete event.cssClass;
    // don't allow dragging or resizing events to different days
    // @ts-ignore
    const sameDay = isSameDay(newStart, newEnd);

    if (!sameDay) {
      return false;
    }

    // don't allow dragging events to the same times as other events
    const overlappingEvent = this.events.find((otherEvent) => {
      return (
        otherEvent !== event &&
        !otherEvent.allDay &&
        // @ts-ignore
        ((otherEvent.start < newStart && newStart < otherEvent.end) ||
          // @ts-ignore
          (otherEvent.start < newEnd && newStart < otherEvent.end))
      );
    });

    if (overlappingEvent) {
      if (addCssClass) {
        event.cssClass = 'invalid-position';
      } else {
        return false;
      }
    }

    return true;
  }
  eventTimesChanged(
    eventTimesChangedEvent: CalendarEventTimesChangedEvent
  ): void {
    delete eventTimesChangedEvent.event.cssClass;
    if (this.validateEventTimesChanged(eventTimesChangedEvent, false)) {
      const { event, newStart, newEnd } = eventTimesChangedEvent;
      event.start = newStart;
      event.end = newEnd;
      this.refresh.next();
    }
  }
}
