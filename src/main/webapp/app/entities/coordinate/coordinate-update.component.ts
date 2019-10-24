import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ICoordinate, Coordinate } from 'app/shared/model/coordinate.model';
import { CoordinateService } from './coordinate.service';

@Component({
  selector: 'jhi-coordinate-update',
  templateUrl: './coordinate-update.component.html'
})
export class CoordinateUpdateComponent implements OnInit {
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    latitude: [],
    longitude: []
  });

  constructor(protected coordinateService: CoordinateService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ coordinate }) => {
      this.updateForm(coordinate);
    });
  }

  updateForm(coordinate: ICoordinate) {
    this.editForm.patchValue({
      id: coordinate.id,
      latitude: coordinate.latitude,
      longitude: coordinate.longitude
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const coordinate = this.createFromForm();
    if (coordinate.id !== undefined) {
      this.subscribeToSaveResponse(this.coordinateService.update(coordinate));
    } else {
      this.subscribeToSaveResponse(this.coordinateService.create(coordinate));
    }
  }

  private createFromForm(): ICoordinate {
    return {
      ...new Coordinate(),
      id: this.editForm.get(['id']).value,
      latitude: this.editForm.get(['latitude']).value,
      longitude: this.editForm.get(['longitude']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICoordinate>>) {
    result.subscribe(() => this.onSaveSuccess(), () => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
