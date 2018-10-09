import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IGuest } from 'app/shared/model/guest.model';

@Component({
    selector: 'jhi-guest-detail',
    templateUrl: './guest-detail.component.html'
})
export class GuestDetailComponent implements OnInit {
    guest: IGuest;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ guest }) => {
            this.guest = guest;
        });
    }

    previousState() {
        window.history.back();
    }
}
