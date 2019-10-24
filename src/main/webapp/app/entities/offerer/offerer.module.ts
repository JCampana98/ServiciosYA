import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ServiciosYaSharedModule } from 'app/shared';
import {
  OffererComponent,
  OffererDetailComponent,
  OffererUpdateComponent,
  OffererDeletePopupComponent,
  OffererDeleteDialogComponent,
  offererRoute,
  offererPopupRoute
} from './';

const ENTITY_STATES = [...offererRoute, ...offererPopupRoute];

@NgModule({
  imports: [ServiciosYaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    OffererComponent,
    OffererDetailComponent,
    OffererUpdateComponent,
    OffererDeleteDialogComponent,
    OffererDeletePopupComponent
  ],
  entryComponents: [OffererComponent, OffererUpdateComponent, OffererDeleteDialogComponent, OffererDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServiciosYaOffererModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
