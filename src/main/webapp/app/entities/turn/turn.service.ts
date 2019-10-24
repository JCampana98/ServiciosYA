import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITurn } from 'app/shared/model/turn.model';

type EntityResponseType = HttpResponse<ITurn>;
type EntityArrayResponseType = HttpResponse<ITurn[]>;

@Injectable({ providedIn: 'root' })
export class TurnService {
  public resourceUrl = SERVER_API_URL + 'api/turns';

  constructor(protected http: HttpClient) {}

  create(turn: ITurn): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(turn);
    return this.http
      .post<ITurn>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(turn: ITurn): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(turn);
    return this.http
      .put<ITurn>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITurn>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITurn[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(turn: ITurn): ITurn {
    const copy: ITurn = Object.assign({}, turn, {
      orderDate: turn.orderDate != null && turn.orderDate.isValid() ? turn.orderDate.toJSON() : null,
      workDate: turn.workDate != null && turn.workDate.isValid() ? turn.workDate.toJSON() : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.orderDate = res.body.orderDate != null ? moment(res.body.orderDate) : null;
      res.body.workDate = res.body.workDate != null ? moment(res.body.workDate) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((turn: ITurn) => {
        turn.orderDate = turn.orderDate != null ? moment(turn.orderDate) : null;
        turn.workDate = turn.workDate != null ? moment(turn.workDate) : null;
      });
    }
    return res;
  }
}
