import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IOfferer, Offerer } from 'app/shared/model/offerer.model';
import { OffererService } from './offerer.service';
import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from 'app/entities/location';

@Component({
  selector: 'jhi-offerer-update',
  templateUrl: './offerer-update.component.html'
})
export class OffererUpdateComponent implements OnInit {
  isSaving: boolean;

  locations: ILocation[];

  editForm = this.fb.group({
    id: [],
    gender: [null, [Validators.required]],
    phoneNumber: [null, [Validators.required]],
    birthday: [null, [Validators.required]],
    reputation: [],
    locationId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected offererService: OffererService,
    protected locationService: LocationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ offerer }) => {
      this.updateForm(offerer);
    });
    this.locationService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ILocation[]>) => mayBeOk.ok),
        map((response: HttpResponse<ILocation[]>) => response.body)
      )
      .subscribe((res: ILocation[]) => (this.locations = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(offerer: IOfferer) {
    this.editForm.patchValue({
      id: offerer.id,
      gender: offerer.gender,
      phoneNumber: offerer.phoneNumber,
      birthday: offerer.birthday != null ? offerer.birthday.format(DATE_TIME_FORMAT) : null,
      reputation: offerer.reputation,
      locationId: offerer.locationId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const offerer = this.createFromForm();
    if (offerer.id !== undefined) {
      this.subscribeToSaveResponse(this.offererService.update(offerer));
    } else {
      this.subscribeToSaveResponse(this.offererService.create(offerer));
    }
  }

  private createFromForm(): IOfferer {
    return {
      ...new Offerer(),
      id: this.editForm.get(['id']).value,
      gender: this.editForm.get(['gender']).value,
      phoneNumber: this.editForm.get(['phoneNumber']).value,
      birthday: this.editForm.get(['birthday']).value != null ? moment(this.editForm.get(['birthday']).value, DATE_TIME_FORMAT) : undefined,
      reputation: this.editForm.get(['reputation']).value,
      locationId: this.editForm.get(['locationId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOfferer>>) {
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

  trackLocationById(index: number, item: ILocation) {
    return item.id;
  }
}
