/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { ServiciosYaTestModule } from '../../../test.module';
import { ProfessionDeleteDialogComponent } from 'app/entities/profession/profession-delete-dialog.component';
import { ProfessionService } from 'app/entities/profession/profession.service';

describe('Component Tests', () => {
  describe('Profession Management Delete Component', () => {
    let comp: ProfessionDeleteDialogComponent;
    let fixture: ComponentFixture<ProfessionDeleteDialogComponent>;
    let service: ProfessionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ServiciosYaTestModule],
        declarations: [ProfessionDeleteDialogComponent]
      })
        .overrideTemplate(ProfessionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProfessionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProfessionService);
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
