/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { JhHotelTestModule } from '../../../test.module';
import { GuestDeleteDialogComponent } from 'app/entities/guest/guest-delete-dialog.component';
import { GuestService } from 'app/entities/guest/guest.service';

describe('Component Tests', () => {
    describe('Guest Management Delete Component', () => {
        let comp: GuestDeleteDialogComponent;
        let fixture: ComponentFixture<GuestDeleteDialogComponent>;
        let service: GuestService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhHotelTestModule],
                declarations: [GuestDeleteDialogComponent]
            })
                .overrideTemplate(GuestDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GuestDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GuestService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
