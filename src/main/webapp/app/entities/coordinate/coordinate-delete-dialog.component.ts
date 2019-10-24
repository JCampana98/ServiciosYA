import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICoordinate } from 'app/shared/model/coordinate.model';
import { CoordinateService } from './coordinate.service';

@Component({
  selector: 'jhi-coordinate-delete-dialog',
  templateUrl: './coordinate-delete-dialog.component.html'
})
export class CoordinateDeleteDialogComponent {
  coordinate: ICoordinate;

  constructor(
    protected coordinateService: CoordinateService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.coordinateService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'coordinateListModification',
        content: 'Deleted an coordinate'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-coordinate-delete-popup',
  template: ''
})
export class CoordinateDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ coordinate }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CoordinateDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.coordinate = coordinate;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/coordinate', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/coordinate', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
