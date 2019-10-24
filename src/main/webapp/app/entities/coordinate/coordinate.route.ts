import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Coordinate } from 'app/shared/model/coordinate.model';
import { CoordinateService } from './coordinate.service';
import { CoordinateComponent } from './coordinate.component';
import { CoordinateDetailComponent } from './coordinate-detail.component';
import { CoordinateUpdateComponent } from './coordinate-update.component';
import { CoordinateDeletePopupComponent } from './coordinate-delete-dialog.component';
import { ICoordinate } from 'app/shared/model/coordinate.model';

@Injectable({ providedIn: 'root' })
export class CoordinateResolve implements Resolve<ICoordinate> {
  constructor(private service: CoordinateService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICoordinate> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Coordinate>) => response.ok),
        map((coordinate: HttpResponse<Coordinate>) => coordinate.body)
      );
    }
    return of(new Coordinate());
  }
}

export const coordinateRoute: Routes = [
  {
    path: '',
    component: CoordinateComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'serviciosYaApp.coordinate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CoordinateDetailComponent,
    resolve: {
      coordinate: CoordinateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'serviciosYaApp.coordinate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CoordinateUpdateComponent,
    resolve: {
      coordinate: CoordinateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'serviciosYaApp.coordinate.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CoordinateUpdateComponent,
    resolve: {
      coordinate: CoordinateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'serviciosYaApp.coordinate.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const coordinatePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: CoordinateDeletePopupComponent,
    resolve: {
      coordinate: CoordinateResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'serviciosYaApp.coordinate.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
