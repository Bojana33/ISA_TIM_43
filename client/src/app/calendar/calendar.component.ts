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
  @Input() cottageId: any;


  constructor(private configService: ConfigService,
              private reservationService: ReservationService,
              private datePipe: DatePipe) {}

  viewDate: Date = new Date();
  refresh = new Subject<void>();
  events: CalendarEvent[] = [
    // {
    //   start: subDays(startOfDay(new Date()), 1),
    //   end: addDays(new Date(), 1),
    //   title: 'A 3 day event',
    //   color: colors.blue,
    //   allDay: true,
    //   resizable: {
    //     beforeStart: true,
    //     afterEnd: true,
    //   },
    //   draggable: true,
    // },
    // {
    //   start: addHours(startOfDay(setDay(new Date(), 3)), 2),
    //   end: subSeconds(addHours(startOfDay(setDay(new Date(), 3)), 3), 1),
    //   title: 'An short event',
    //   color: colors.yellow,
    //   resizable: {
    //     beforeStart: true,
    //     afterEnd: true,
    //   },
    //   draggable: true,
    // },
    // {
    //   start: addHours(startOfDay(setDay(new Date(), 3)), 5),
    //   end: subSeconds(addHours(startOfDay(setDay(new Date(), 3)), 10), 1),
    //   title: 'A draggable and resizable event',
    //   color: colors.yellow,
    //   resizable: {
    //     beforeStart: true,
    //     afterEnd: true,
    //   },
    //   draggable: true,
    // },
  ];

  ngOnInit(): void {
    this.getReservationsForCottage(this.cottageId);
  }
  getReservationsForCottage(cottageId: number): void{

    this.reservationService.getReservationsForEntity(cottageId).subscribe(
      (modelData: ReservationDTO[]) => {
        modelData.forEach((item) => {
          let eventColor = colors.green;
          if (item.reservationStatus.toString() === 'RESERVED'){
            eventColor = colors.red;
          }
          this.events.push({
            id: item.reservedPeriod.id,
            start: new Date(item.reservedPeriod.startDate),
            end: new Date(item.reservedPeriod.endDate),
            title: 'Termini slobodni za rezervisanje',
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
