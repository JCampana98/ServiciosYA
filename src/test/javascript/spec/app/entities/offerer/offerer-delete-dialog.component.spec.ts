/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ServiciosYaTestModule } from '../../../test.module';
import { OffererDeleteDialogComponent } from 'app/entities/offerer/offerer-delete-dialog.component';
import { OffererService } from 'app/entities/offerer/offerer.service';

describe('Component Tests', () => {
  describe('Offerer Management Delete Component', () => {
    let comp: OffererDeleteDialogComponent;
    let fixture: ComponentFixture<OffererDeleteDialogComponent>;
    let service: OffererService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ServiciosYaTestModule],
        declarations: [OffererDeleteDialogComponent]
      })
        .overrideTemplate(OffererDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OffererDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OffererService);
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
