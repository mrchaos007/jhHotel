import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Guest } from 'app/shared/model/guest.model';
import { GuestService } from './guest.service';
import { GuestComponent } from './guest.component';
import { GuestDetailComponent } from './guest-detail.component';
import { GuestUpdateComponent } from './guest-update.component';
import { GuestDeletePopupComponent } from './guest-delete-dialog.component';
import { IGuest } from 'app/shared/model/guest.model';

@Injectable({ providedIn: 'root' })
export class GuestResolve implements Resolve<IGuest> {
    constructor(private service: GuestService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((guest: HttpResponse<Guest>) => guest.body));
        }
        return of(new Guest());
    }
}

export const guestRoute: Routes = [
    {
        path: 'guest',
        component: GuestComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Guests'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'guest/:id/view',
        component: GuestDetailComponent,
        resolve: {
            guest: GuestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Guests'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'guest/new',
        component: GuestUpdateComponent,
        resolve: {
            guest: GuestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Guests'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'guest/:id/edit',
        component: GuestUpdateComponent,
        resolve: {
            guest: GuestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Guests'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const guestPopupRoute: Routes = [
    {
        path: 'guest/:id/delete',
        component: GuestDeletePopupComponent,
        resolve: {
            guest: GuestResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Guests'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
