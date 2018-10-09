export interface IReview {
    id?: number;
    rating?: number;
    revText?: any;
    guestId?: number;
}

export class Review implements IReview {
    constructor(public id?: number, public rating?: number, public revText?: any, public guestId?: number) {}
}
