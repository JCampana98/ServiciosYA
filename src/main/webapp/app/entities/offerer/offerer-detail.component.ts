import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOfferer } from 'app/shared/model/offerer.model';

@Component({
  selector: 'jhi-offerer-detail',
  templateUrl: './offerer-detail.component.html'
})
export class OffererDetailComponent implements OnInit {
  offerer: IOfferer;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ offerer }) => {
      this.offerer = offerer;
    });
  }

  previousState() {
    window.history.back();
  }
}
