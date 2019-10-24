import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ServiciosYaSharedModule } from 'app/shared';
import {
  ProfessionComponent,
  ProfessionDetailComponent,
  ProfessionUpdateComponent,
  ProfessionDeletePopupComponent,
  ProfessionDeleteDialogComponent,
  professionRoute,
  professionPopupRoute
} from './';

const ENTITY_STATES = [...professionRoute, ...professionPopupRoute];

@NgModule({
  imports: [ServiciosYaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ProfessionComponent,
    ProfessionDetailComponent,
    ProfessionUpdateComponent,
    ProfessionDeleteDialogComponent,
    ProfessionDeletePopupComponent
  ],
  entryComponents: [ProfessionComponent, ProfessionUpdateComponent, ProfessionDeleteDialogComponent, ProfessionDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServiciosYaProfessionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
