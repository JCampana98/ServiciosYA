/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ServiciosYaTestModule } from '../../../test.module';
import { OffererDetailComponent } from 'app/entities/offerer/offerer-detail.component';
import { Offerer } from 'app/shared/model/offerer.model';

describe('Component Tests', () => {
  describe('Offerer Management Detail Component', () => {
    let comp: OffererDetailComponent;
    let fixture: ComponentFixture<OffererDetailComponent>;
    const route = ({ data: of({ offerer: new Offerer(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ServiciosYaTestModule],
        declarations: [OffererDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OffererDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OffererDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.offerer).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
