export interface IGuest {
    id?: number;
    name?: string;
}

export class Guest implements IGuest {
    constructor(public id?: number, public name?: string) {}
}
