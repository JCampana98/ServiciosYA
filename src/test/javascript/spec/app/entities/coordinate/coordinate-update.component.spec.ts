/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { ServiciosYaTestModule } from '../../../test.module';
import { CoordinateUpdateComponent } from 'app/entities/coordinate/coordinate-update.component';
import { CoordinateService } from 'app/entities/coordinate/coordinate.service';
import { Coordinate } from 'app/shared/model/coordinate.model';

describe('Component Tests', () => {
  describe('Coordinate Management Update Component', () => {
    let comp: CoordinateUpdateComponent;
    let fixture: ComponentFixture<CoordinateUpdateComponent>;
    let service: CoordinateService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ServiciosYaTestModule],
        declarations: [CoordinateUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CoordinateUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CoordinateUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CoordinateService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Coordinate(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Coordinate();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
