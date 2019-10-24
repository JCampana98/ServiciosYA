import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICoordinate } from 'app/shared/model/coordinate.model';

@Component({
  selector: 'jhi-coordinate-detail',
  templateUrl: './coordinate-detail.component.html'
})
export class CoordinateDetailComponent implements OnInit {
  coordinate: ICoordinate;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ coordinate }) => {
      this.coordinate = coordinate;
    });
  }

  previousState() {
    window.history.back();
  }
}
