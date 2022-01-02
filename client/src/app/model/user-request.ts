import { Address } from "./address";

export class UserRequest {
        public id!: number;
        public firstName!: string;
        public surname!: string;
        public email!: string;
        public confirmed!: boolean;
        public registrationExplanation!: string;
        public rejectionReason!: string;
        public addressDTO: Address = new Address();
        public password!: string;
        public phoneNumber!: string;
}
