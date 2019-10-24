import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { ITurn, Turn } from 'app/shared/model/turn.model';
import { TurnService } from './turn.service';
import { ITransaction } from 'app/shared/model/transaction.model';
import { TransactionService } from 'app/entities/transaction';
import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from 'app/entities/location';
import { IOfferer } from 'app/shared/model/offerer.model';
import { OffererService } from 'app/entities/offerer';
import { IPerson } from 'app/shared/model/person.model';
import { PersonService } from 'app/entities/person';

@Component({
  selector: 'jhi-turn-update',
  templateUrl: './turn-update.component.html'
})
export class TurnUpdateComponent implements OnInit {
  isSaving: boolean;

  transactions: ITransaction[];

  locations: ILocation[];

  offerers: IOfferer[];

  people: IPerson[];

  editForm = this.fb.group({
    id: [],
    orderDate: [],
    workDate: [],
    cost: [],
    description: [null, [Validators.required]],
    available: [],
    turnState: [],
    transactionId: [],
    locationId: [],
    offererId: [],
    personId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected turnService: TurnService,
    protected transactionService: TransactionService,
    protected locationService: LocationService,
    protected offererService: OffererService,
    protected personService: PersonService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ turn }) => {
      this.updateForm(turn);
    });
    this.transactionService
      .query({ filter: 'turn-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ITransaction[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITransaction[]>) => response.body)
      )
      .subscribe(
        (res: ITransaction[]) => {
          if (!this.editForm.get('transactionId').value) {
            this.transactions = res;
          } else {
            this.transactionService
              .find(this.editForm.get('transactionId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ITransaction>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ITransaction>) => subResponse.body)
              )
              .subscribe(
                (subRes: ITransaction) => (this.transactions = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.locationService
      .query({ filter: 'turn-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ILocation[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILocation[]>) => response.body)
      )
      .subscribe(
        (res: ILocation[]) => {
          if (!this.editForm.get('locationId').value) {
            this.locations = res;
          } else {
            this.locationService
              .find(this.editForm.get('locationId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ILocation>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ILocation>) => subResponse.body)
              )
              .subscribe(
                (subRes: ILocation) => (this.locations = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.offererService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IOfferer[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOfferer[]>) => response.body)
      )
      .subscribe((res: IOfferer[]) => (this.offerers = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.personService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IPerson[]>) => mayBeOk.ok),
        map((response: HttpResponse<IPerson[]>) => response.body)
      )
      .subscribe((res: IPerson[]) => (this.people = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(turn: ITurn) {
    this.editForm.patchValue({
      id: turn.id,
      orderDate: turn.orderDate != null ? turn.orderDate.format(DATE_TIME_FORMAT) : null,
      workDate: turn.workDate != null ? turn.workDate.format(DATE_TIME_FORMAT) : null,
      cost: turn.cost,
      description: turn.description,
      available: turn.available,
      turnState: turn.turnState,
      transactionId: turn.transactionId,
      locationId: turn.locationId,
      offererId: turn.offererId,
      personId: turn.personId
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const turn = this.createFromForm();
    if (turn.id !== undefined) {
      this.subscribeToSaveResponse(this.turnService.update(turn));
    } else {
      this.subscribeToSaveResponse(this.turnService.create(turn));
    }
  }

  private createFromForm(): ITurn {
    return {
      ...new Turn(),
      id: this.editForm.get(['id']).value,
      orderDate:
        this.editForm.get(['orderDate']).value != null ? moment(this.editForm.get(['orderDate']).value, DATE_TIME_FORMAT) : undefined,
      workDate: this.editForm.get(['workDate']).value != null ? moment(this.editForm.get(['workDate']).value, DATE_TIME_FORMAT) : undefined,
      cost: this.editForm.get(['cost']).value,
      description: this.editForm.get(['description']).value,
      available: this.editForm.get(['available']).value,
      turnState: this.editForm.get(['turnState']).value,
      transactionId: this.editForm.get(['transactionId']).value,
      locationId: this.editForm.get(['locationId']).value,
      offererId: this.editForm.get(['offererId']).value,
      personId: this.editForm.get(['personId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITurn>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackTransactionById(index: number, item: ITransaction) {
    return item.id;
  }

  trackLocationById(index: number, item: ILocation) {
    return item.id;
  }

  trackOffererById(index: number, item: IOfferer) {
    return item.id;
  }

  trackPersonById(index: number, item: IPerson) {
    return item.id;
  }
}
