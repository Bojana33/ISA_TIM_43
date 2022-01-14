import { AvailabilityType } from './../enum/availability-type.enum';
import { PeriodDto } from './period-dto.model';

export class InstructorAvailabilityDto {
    public id!: number;
    public periodDTO!: PeriodDto;
    public ownerId!: number;
    public availabilityType!: AvailabilityType;

}
