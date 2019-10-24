import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOfferer } from 'app/shared/model/offerer.model';
import { OffererService } from './offerer.service';

@Component({
  selector: 'jhi-offerer-delete-dialog',
  templateUrl: './offerer-delete-dialog.component.html'
})
export class OffererDeleteDialogComponent {
  offerer: IOfferer;

  constructor(protected offererService: OffererService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.offererService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'offererListModification',
        content: 'Deleted an offerer'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-offerer-delete-popup',
  template: ''
})
export class OffererDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ offerer }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(OffererDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.offerer = offerer;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/offerer', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/offerer', { outlets: { popup: null } }]);
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
