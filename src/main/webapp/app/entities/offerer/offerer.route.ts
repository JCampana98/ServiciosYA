import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Offerer } from 'app/shared/model/offerer.model';
import { OffererService } from './offerer.service';
import { OffererComponent } from './offerer.component';
import { OffererDetailComponent } from './offerer-detail.component';
import { OffererUpdateComponent } from './offerer-update.component';
import { OffererDeletePopupComponent } from './offerer-delete-dialog.component';
import { IOfferer } from 'app/shared/model/offerer.model';

@Injectable({ providedIn: 'root' })
export class OffererResolve implements Resolve<IOfferer> {
  constructor(private service: OffererService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IOfferer> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Offerer>) => response.ok),
        map((offerer: HttpResponse<Offerer>) => offerer.body)
      );
    }
    return of(new Offerer());
  }
}

export const offererRoute: Routes = [
  {
    path: '',
    component: OffererComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'serviciosYaApp.offerer.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OffererDetailComponent,
    resolve: {
      offerer: OffererResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'serviciosYaApp.offerer.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OffererUpdateComponent,
    resolve: {
      offerer: OffererResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'serviciosYaApp.offerer.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OffererUpdateComponent,
    resolve: {
      offerer: OffererResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'serviciosYaApp.offerer.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const offererPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: OffererDeletePopupComponent,
    resolve: {
      offerer: OffererResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'serviciosYaApp.offerer.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
