import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { JhHotelGuestModule } from './guest/guest.module';
import { JhHotelReservationModule } from './reservation/reservation.module';
import { JhHotelReviewModule } from './review/review.module';
import { JhHotelRoomModule } from './room/room.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        JhHotelGuestModule,
        JhHotelReservationModule,
        JhHotelReviewModule,
        JhHotelRoomModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhHotelEntityModule {}
