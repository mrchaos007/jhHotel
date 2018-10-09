import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IGuest } from 'app/shared/model/guest.model';
import { GuestService } from './guest.service';

@Component({
    selector: 'jhi-guest-update',
    templateUrl: './guest-update.component.html'
})
export class GuestUpdateComponent implements OnInit {
    private _guest: IGuest;
    isSaving: boolean;

    constructor(private guestService: GuestService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ guest }) => {
            this.guest = guest;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.guest.id !== undefined) {
            this.subscribeToSaveResponse(this.guestService.update(this.guest));
        } else {
            this.subscribeToSaveResponse(this.guestService.create(this.guest));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IGuest>>) {
        result.subscribe((res: HttpResponse<IGuest>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get guest() {
        return this._guest;
    }

    set guest(guest: IGuest) {
        this._guest = guest;
    }
}
