import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IReservation } from 'app/shared/model/reservation.model';
import { ReservationService } from './reservation.service';
import { IRoom, Room } from 'app/shared/model/room.model';
import { RoomService } from 'app/entities/room';
import { IGuest } from 'app/shared/model/guest.model';
// import { GuestService } from 'app/entities/guest';
import { UserService, User, IUser } from 'app/core';

@Component({
    selector: 'jhi-reservation-update',
    templateUrl: './reservation-update.component.html'
})
export class ReservationUpdateComponent implements OnInit {
    private _reservation: IReservation;
    isSaving: boolean;

    rooms: IRoom[];

    // guests: IGuest[];
    guests: IUser[];
    checkInDp: any;
    checkOutDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private reservationService: ReservationService,
        private roomService: RoomService,
        // private guestService: GuestService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ reservation }) => {
            this.reservation = reservation;
        });
        this.roomService.query({ filter: 'reservation-is-null' }).subscribe(
            (res: HttpResponse<IRoom[]>) => {
                if (!this.reservation.roomDTO) {
                    this.rooms = res.body;
                } else {
                    this.roomService.find(this.reservation.roomDTO.id).subscribe(
                        (subRes: HttpResponse<IRoom>) => {
                            this.rooms = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.guests = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );

    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.reservation.id !== undefined) {
            this.subscribeToSaveResponse(this.reservationService.update(this.reservation));
        } else {
            this.subscribeToSaveResponse(this.reservationService.create(this.reservation));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IReservation>>) {
        result.subscribe((res: HttpResponse<IReservation>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackRoomById(index: number, item: IRoom) {
        return item.id;
    }

    trackGuestById(index: number, item: IUser) {
        return item.id;
    }
    get reservation() {
        return this._reservation;
    }

    set reservation(reservation: IReservation) {
        this._reservation = reservation;
    }
}
