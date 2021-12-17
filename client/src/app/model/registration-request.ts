import { OwnerType } from "../enum/owner-type";
import { Address } from "./address";

export class RegistrationRequest {
        public id!: number;
        public firstName!: string;
        public surname!: string;
        public email!: string;
        public confirmed!: boolean;
        public registrationExplanation!: string;
        public rejectionReason!: string;
        public ownerType!: OwnerType;
        public addressDTO: Address = new Address();
        public password!: string;
        public phoneNumber!: string;
}
