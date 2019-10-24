import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Profession } from 'app/shared/model/profession.model';
import { ProfessionService } from './profession.service';
import { ProfessionComponent } from './profession.component';
import { ProfessionDetailComponent } from './profession-detail.component';
import { ProfessionUpdateComponent } from './profession-update.component';
import { ProfessionDeletePopupComponent } from './profession-delete-dialog.component';
import { IProfession } from 'app/shared/model/profession.model';

@Injectable({ providedIn: 'root' })
export class ProfessionResolve implements Resolve<IProfession> {
  constructor(private service: ProfessionService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProfession> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Profession>) => response.ok),
        map((profession: HttpResponse<Profession>) => profession.body)
      );
    }
    return of(new Profession());
  }
}

export const professionRoute: Routes = [
  {
    path: '',
    component: ProfessionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'serviciosYaApp.profession.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProfessionDetailComponent,
    resolve: {
      profession: ProfessionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'serviciosYaApp.profession.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProfessionUpdateComponent,
    resolve: {
      profession: ProfessionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'serviciosYaApp.profession.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProfessionUpdateComponent,
    resolve: {
      profession: ProfessionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'serviciosYaApp.profession.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const professionPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ProfessionDeletePopupComponent,
    resolve: {
      profession: ProfessionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'serviciosYaApp.profession.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
