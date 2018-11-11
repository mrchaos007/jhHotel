import { Moment } from 'moment';

export interface IUser {
    id?: number;
    checkIn?: Moment;
    checkOut?: Moment;
    roomId?: number;
    userId?: number;
}

export class User implements IUser {
    constructor(public id?: number, public checkIn?: Moment, public checkOut?: Moment, public roomId?: number, public userId?: number) {}
}
