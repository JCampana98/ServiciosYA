import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { ServiciosYaSharedModule } from 'app/shared';
import {
  TransactionComponent,
  TransactionDetailComponent,
  TransactionUpdateComponent,
  TransactionDeletePopupComponent,
  TransactionDeleteDialogComponent,
  transactionRoute,
  transactionPopupRoute
} from './';

const ENTITY_STATES = [...transactionRoute, ...transactionPopupRoute];

@NgModule({
  imports: [ServiciosYaSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TransactionComponent,
    TransactionDetailComponent,
    TransactionUpdateComponent,
    TransactionDeleteDialogComponent,
    TransactionDeletePopupComponent
  ],
  entryComponents: [TransactionComponent, TransactionUpdateComponent, TransactionDeleteDialogComponent, TransactionDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServiciosYaTransactionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
