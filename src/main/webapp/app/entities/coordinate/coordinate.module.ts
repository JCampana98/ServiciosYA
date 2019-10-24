import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ServiciosYaSharedModule } from 'app/shared';
import {
  CoordinateComponent,
  CoordinateDetailComponent,
  CoordinateUpdateComponent,
  CoordinateDeletePopupComponent,
  CoordinateDeleteDialogComponent,
  coordinateRoute,
  coordinatePopupRoute
} from './';

const ENTITY_STATES = [...coordinateRoute, ...coordinatePopupRoute];

@NgModule({
  imports: [ServiciosYaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    CoordinateComponent,
    CoordinateDetailComponent,
    CoordinateUpdateComponent,
    CoordinateDeleteDialogComponent,
    CoordinateDeletePopupComponent
  ],
  entryComponents: [CoordinateComponent, CoordinateUpdateComponent, CoordinateDeleteDialogComponent, CoordinateDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServiciosYaCoordinateModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
