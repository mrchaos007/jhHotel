import { Room } from 'app/shared/model/room.model';
import { Moment } from 'moment';
import { User } from 'app/core';

export interface IReservation {
    id?: number;
    checkIn?: Moment;
    checkOut?: Moment;
    roomDTO?: Room;
    userDTO?: User;
}

export class Reservation implements IReservation {
    constructor(public id?: number, public checkIn?: Moment, public checkOut?: Moment, public roomDTO?: Room, public userDTO?: User) {}
}
