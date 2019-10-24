import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ServiciosYaSharedModule } from 'app/shared';
import {
  TurnComponent,
  TurnDetailComponent,
  TurnUpdateComponent,
  TurnDeletePopupComponent,
  TurnDeleteDialogComponent,
  turnRoute,
  turnPopupRoute
} from './';

const ENTITY_STATES = [...turnRoute, ...turnPopupRoute];

@NgModule({
  imports: [ServiciosYaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [TurnComponent, TurnDetailComponent, TurnUpdateComponent, TurnDeleteDialogComponent, TurnDeletePopupComponent],
  entryComponents: [TurnComponent, TurnUpdateComponent, TurnDeleteDialogComponent, TurnDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServiciosYaTurnModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
