import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ILocation, Location } from 'app/shared/model/location.model';
import { LocationService } from './location.service';
import { ICoordinate } from 'app/shared/model/coordinate.model';
import { CoordinateService } from 'app/entities/coordinate';

@Component({
  selector: 'jhi-location-update',
  templateUrl: './location-update.component.html'
})
export class LocationUpdateComponent implements OnInit {
  isSaving: boolean;

  coordinates: ICoordinate[];

  editForm = this.fb.group({
    id: [],
    streetAddress: [null, [Validators.required]],
    streetNumber: [null, [Validators.required]],
    flatNumber: [],
    safeZone: [null, [Validators.required]],
    country: [null, [Validators.required]],
    zipCode: [null, [Validators.required]],
    city: [null, [Validators.required]],
    province: [null, [Validators.required]],
    department: [],
    coordinateId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected locationService: LocationService,
    protected coordinateService: CoordinateService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ location }) => {
      this.updateForm(location);
    });
    this.coordinateService
      .query({ filter: 'location-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ICoordinate[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICoordinate[]>) => response.body)
      )
      .subscribe(
        (res: ICoordinate[]) => {
          if (!this.editForm.get('coordinateId').value) {
            this.coordinates = res;
          } else {
            this.coordinateService
              .find(this.editForm.get('coordinateId').value)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ICoordinate>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ICoordinate>) => subResponse.body)
              )
              .subscribe(
                (subRes: ICoordinate) => (this.coordinates = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  updateForm(location: ILocation) {
    this.editForm.patchValue({
      id: location.id,
      streetAddress: location.streetAddress,
      streetNumber: location.streetNumber,
      flatNumber: location.flatNumber,
      safeZone: location.safeZone,
      country: location.country,
      zipCode: location.zipCode,
      city: location.city,
      province: location.province,
      department: location.department,
      coordinateId: location.coordinateId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const location = this.createFromForm();
    if (location.id !== undefined) {
      this.subscribeToSaveResponse(this.locationService.update(location));
    } else {
      this.subscribeToSaveResponse(this.locationService.create(location));
    }
  }

  private createFromForm(): ILocation {
    return {
      ...new Location(),
      id: this.editForm.get(['id']).value,
      streetAddress: this.editForm.get(['streetAddress']).value,
      streetNumber: this.editForm.get(['streetNumber']).value,
      flatNumber: this.editForm.get(['flatNumber']).value,
      safeZone: this.editForm.get(['safeZone']).value,
      country: this.editForm.get(['country']).value,
      zipCode: this.editForm.get(['zipCode']).value,
      city: this.editForm.get(['city']).value,
      province: this.editForm.get(['province']).value,
      department: this.editForm.get(['department']).value,
      coordinateId: this.editForm.get(['coordinateId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILocation>>) {
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

  trackCoordinateById(index: number, item: ICoordinate) {
    return item.id;
  }
}
