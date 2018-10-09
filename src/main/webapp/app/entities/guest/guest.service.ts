import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IGuest } from 'app/shared/model/guest.model';

type EntityResponseType = HttpResponse<IGuest>;
type EntityArrayResponseType = HttpResponse<IGuest[]>;

@Injectable({ providedIn: 'root' })
export class GuestService {
    private resourceUrl = SERVER_API_URL + 'api/guests';

    constructor(private http: HttpClient) {}

    create(guest: IGuest): Observable<EntityResponseType> {
        return this.http.post<IGuest>(this.resourceUrl, guest, { observe: 'response' });
    }

    update(guest: IGuest): Observable<EntityResponseType> {
        return this.http.put<IGuest>(this.resourceUrl, guest, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IGuest>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IGuest[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
