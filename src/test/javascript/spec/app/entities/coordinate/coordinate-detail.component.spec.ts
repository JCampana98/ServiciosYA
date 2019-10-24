/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ServiciosYaTestModule } from '../../../test.module';
import { CoordinateDetailComponent } from 'app/entities/coordinate/coordinate-detail.component';
import { Coordinate } from 'app/shared/model/coordinate.model';

describe('Component Tests', () => {
  describe('Coordinate Management Detail Component', () => {
    let comp: CoordinateDetailComponent;
    let fixture: ComponentFixture<CoordinateDetailComponent>;
    const route = ({ data: of({ coordinate: new Coordinate(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ServiciosYaTestModule],
        declarations: [CoordinateDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CoordinateDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CoordinateDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.coordinate).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
