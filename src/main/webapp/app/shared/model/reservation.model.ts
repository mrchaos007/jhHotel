import { Moment } from 'moment';

export interface IReservation {
    id?: number;
    checkIn?: Moment;
    checkOut?: Moment;
    roomId?: number;
    guestId?: number;
}

export class Reservation implements IReservation {
    constructor(public id?: number, public checkIn?: Moment, public checkOut?: Moment, public roomId?: number, public guestId?: number) {}
}
