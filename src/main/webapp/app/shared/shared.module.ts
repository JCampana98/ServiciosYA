import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { ServiciosYaSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [ServiciosYaSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [ServiciosYaSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServiciosYaSharedModule {
  static forRoot() {
    return {
      ngModule: ServiciosYaSharedModule
    };
  }
}
