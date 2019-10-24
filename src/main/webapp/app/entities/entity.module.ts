import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'coordinate',
        loadChildren: () => import('./coordinate/coordinate.module').then(m => m.ServiciosYaCoordinateModule)
      },
      {
        path: 'location',
        loadChildren: () => import('./location/location.module').then(m => m.ServiciosYaLocationModule)
      },
      {
        path: 'person',
        loadChildren: () => import('./person/person.module').then(m => m.ServiciosYaPersonModule)
      },
      {
        path: 'offerer',
        loadChildren: () => import('./offerer/offerer.module').then(m => m.ServiciosYaOffererModule)
      },
      {
        path: 'profession',
        loadChildren: () => import('./profession/profession.module').then(m => m.ServiciosYaProfessionModule)
      },
      {
        path: 'turn',
        loadChildren: () => import('./turn/turn.module').then(m => m.ServiciosYaTurnModule)
      },
      {
        path: 'comment',
        loadChildren: () => import('./comment/comment.module').then(m => m.ServiciosYaCommentModule)
      },
      {
        path: 'transaction',
        loadChildren: () => import('./transaction/transaction.module').then(m => m.ServiciosYaTransactionModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ServiciosYaEntityModule {}
