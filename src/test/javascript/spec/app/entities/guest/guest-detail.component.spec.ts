/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhHotelTestModule } from '../../../test.module';
import { GuestDetailComponent } from 'app/entities/guest/guest-detail.component';
import { Guest } from 'app/shared/model/guest.model';

describe('Component Tests', () => {
    describe('Guest Management Detail Component', () => {
        let comp: GuestDetailComponent;
        let fixture: ComponentFixture<GuestDetailComponent>;
        const route = ({ data: of({ guest: new Guest(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhHotelTestModule],
                declarations: [GuestDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(GuestDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GuestDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.guest).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
