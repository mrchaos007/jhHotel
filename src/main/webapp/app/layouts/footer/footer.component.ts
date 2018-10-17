import { Component } from '@angular/core';

@Component({
    selector: 'jhi-footer',
    templateUrl: './footer.component.html'
})
export class FooterComponent {
    currentDate: number;

    constructor() {
        this.currentDate = new Date().getTime();
    }
}
