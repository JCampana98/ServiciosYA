import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProfession, Profession } from 'app/shared/model/profession.model';
import { ProfessionService } from './profession.service';
import { IOfferer } from 'app/shared/model/offerer.model';
import { OffererService } from 'app/entities/offerer';

@Component({
  selector: 'jhi-profession-update',
  templateUrl: './profession-update.component.html'
})
export class ProfessionUpdateComponent implements OnInit {
  isSaving: boolean;

  offerers: IOfferer[];

  editForm = this.fb.group({
    id: [],
    name: [],
    professionDetail: [],
    offererId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected professionService: ProfessionService,
    protected offererService: OffererService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ profession }) => {
      this.updateForm(profession);
    });
    this.offererService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IOfferer[]>) => mayBeOk.ok),
        map((response: HttpResponse<IOfferer[]>) => response.body)
      )
      .subscribe((res: IOfferer[]) => (this.offerers = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(profession: IProfession) {
    this.editForm.patchValue({
      id: profession.id,
      name: profession.name,
      professionDetail: profession.professionDetail,
      offererId: profession.offererId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const profession = this.createFromForm();
    if (profession.id !== undefined) {
      this.subscribeToSaveResponse(this.professionService.update(profession));
    } else {
      this.subscribeToSaveResponse(this.professionService.create(profession));
    }
  }

  private createFromForm(): IProfession {
    return {
      ...new Profession(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      professionDetail: this.editForm.get(['professionDetail']).value,
      offererId: this.editForm.get(['offererId']).value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfession>>) {
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

  trackOffererById(index: number, item: IOfferer) {
    return item.id;
  }
}
