import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReservation } from 'app/shared/model/reservation.model';

type EntityResponseType = HttpResponse<IReservation>;
type EntityArrayResponseType = HttpResponse<IReservation[]>;

@Injectable({ providedIn: 'root' })
export class ReservationService {
    private resourceUrl = SERVER_API_URL + 'api/reservations';

    constructor(private http: HttpClient) {}

    create(reservation: IReservation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(reservation);
        return this.http
            .post<IReservation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(reservation: IReservation): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(reservation);
        return this.http
            .put<IReservation>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IReservation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IReservation[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(reservation: IReservation): IReservation {
        const copy: IReservation = Object.assign({}, reservation, {
            checkIn: reservation.checkIn != null && reservation.checkIn.isValid() ? reservation.checkIn.format(DATE_FORMAT) : null,
            checkOut: reservation.checkOut != null && reservation.checkOut.isValid() ? reservation.checkOut.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.checkIn = res.body.checkIn != null ? moment(res.body.checkIn) : null;
        res.body.checkOut = res.body.checkOut != null ? moment(res.body.checkOut) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((reservation: IReservation) => {
            reservation.checkIn = reservation.checkIn != null ? moment(reservation.checkIn) : null;
            reservation.checkOut = reservation.checkOut != null ? moment(reservation.checkOut) : null;
        });
        return res;
    }
}
