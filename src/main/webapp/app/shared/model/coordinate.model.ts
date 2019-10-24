export interface ICoordinate {
  id?: number;
  latitude?: string;
  longitude?: string;
}

export class Coordinate implements ICoordinate {
  constructor(public id?: number, public latitude?: string, public longitude?: string) {}
}
