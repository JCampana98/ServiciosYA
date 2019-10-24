import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ITurn } from 'app/shared/model/turn.model';

@Component({
  selector: 'jhi-turn-detail',
  templateUrl: './turn-detail.component.html'
})
export class TurnDetailComponent implements OnInit {
  turn: ITurn;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ turn }) => {
      this.turn = turn;
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }
}
