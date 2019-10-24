import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProfession } from 'app/shared/model/profession.model';

type EntityResponseType = HttpResponse<IProfession>;
type EntityArrayResponseType = HttpResponse<IProfession[]>;

@Injectable({ providedIn: 'root' })
export class ProfessionService {
  public resourceUrl = SERVER_API_URL + 'api/professions';

  constructor(protected http: HttpClient) {}

  create(profession: IProfession): Observable<EntityResponseType> {
    return this.http.post<IProfession>(this.resourceUrl, profession, { observe: 'response' });
  }

  update(profession: IProfession): Observable<EntityResponseType> {
    return this.http.put<IProfession>(this.resourceUrl, profession, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProfession>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProfession[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
