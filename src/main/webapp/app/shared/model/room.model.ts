export const enum RoomType {
    BASIC = 'BASIC',
    SUPERIOR = 'SUPERIOR',
    DELUXE = 'DELUXE'
}

export interface IRoom {
    id?: number;
    type?: RoomType;
    numOfBeds?: number;
    roomNumber?: number;
    imageContentType?: string;
    image?: any;
}

export class Room implements IRoom {
    constructor(
        public id?: number,
        public type?: RoomType,
        public numOfBeds?: number,
        public roomNumber?: number,
        public imageContentType?: string,
        public image?: any
    ) {}
}
