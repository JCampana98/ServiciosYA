import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOfferer } from 'app/shared/model/offerer.model';

type EntityResponseType = HttpResponse<IOfferer>;
type EntityArrayResponseType = HttpResponse<IOfferer[]>;

@Injectable({ providedIn: 'root' })
export class OffererService {
  public resourceUrl = SERVER_API_URL + 'api/offerers';

  constructor(protected http: HttpClient) {}

  create(offerer: IOfferer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(offerer);
    return this.http
      .post<IOfferer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(offerer: IOfferer): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(offerer);
    return this.http
      .put<IOfferer>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IOfferer>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IOfferer[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(offerer: IOfferer): IOfferer {
    const copy: IOfferer = Object.assign({}, offerer, {
      birthday: offerer.birthday != null && offerer.birthday.isValid() ? offerer.birthday.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.birthday = res.body.birthday != null ? moment(res.body.birthday) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((offerer: IOfferer) => {
        offerer.birthday = offerer.birthday != null ? moment(offerer.birthday) : null;
      });
    }
    return res;
  }
}
