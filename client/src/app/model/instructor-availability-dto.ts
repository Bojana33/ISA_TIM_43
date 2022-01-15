import { AvailabilityType } from './../enum/availability-type.enum';
import { PeriodDTO } from './period-dto.model';

export class InstructorAvailabilityDto {
    public id!: number;
    public periodDTO!: PeriodDTO;
    public ownerId!: number;
    public availabilityType!: AvailabilityType;

}
