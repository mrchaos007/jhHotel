import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhHotelSharedModule } from 'app/shared';
import {
    GuestComponent,
    GuestDetailComponent,
    GuestUpdateComponent,
    GuestDeletePopupComponent,
    GuestDeleteDialogComponent,
    guestRoute,
    guestPopupRoute
} from './';

const ENTITY_STATES = [...guestRoute, ...guestPopupRoute];

@NgModule({
    imports: [JhHotelSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [GuestComponent, GuestDetailComponent, GuestUpdateComponent, GuestDeleteDialogComponent, GuestDeletePopupComponent],
    entryComponents: [GuestComponent, GuestUpdateComponent, GuestDeleteDialogComponent, GuestDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhHotelGuestModule {}
