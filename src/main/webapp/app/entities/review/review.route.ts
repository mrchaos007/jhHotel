import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Review } from 'app/shared/model/review.model';
import { ReviewService } from './review.service';
import { ReviewComponent } from './review.component';
import { ReviewDetailComponent } from './review-detail.component';
import { ReviewUpdateComponent } from './review-update.component';
import { ReviewDeletePopupComponent } from './review-delete-dialog.component';
import { IReview } from 'app/shared/model/review.model';

@Injectable({ providedIn: 'root' })
export class ReviewResolve implements Resolve<IReview> {
    constructor(private service: ReviewService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((review: HttpResponse<Review>) => review.body));
        }
        return of(new Review());
    }
}

export const reviewRoute: Routes = [
    {
        path: 'review',
        component: ReviewComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Reviews'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'review/:id/view',
        component: ReviewDetailComponent,
        resolve: {
            review: ReviewResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Reviews'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'review/new',
        component: ReviewUpdateComponent,
        resolve: {
            review: ReviewResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Reviews'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'review/:id/edit',
        component: ReviewUpdateComponent,
        resolve: {
            review: ReviewResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Reviews'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reviewPopupRoute: Routes = [
    {
        path: 'review/:id/delete',
        component: ReviewDeletePopupComponent,
        resolve: {
            review: ReviewResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Reviews'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
