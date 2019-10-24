import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProfession } from 'app/shared/model/profession.model';
import { ProfessionService } from './profession.service';

@Component({
  selector: 'jhi-profession-delete-dialog',
  templateUrl: './profession-delete-dialog.component.html'
})
export class ProfessionDeleteDialogComponent {
  profession: IProfession;

  constructor(
    protected professionService: ProfessionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.professionService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'professionListModification',
        content: 'Deleted an profession'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-profession-delete-popup',
  template: ''
})
export class ProfessionDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ profession }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ProfessionDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.profession = profession;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/profession', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/profession', { outlets: { popup: null } }]);
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
