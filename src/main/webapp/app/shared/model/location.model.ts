import { IPerson } from 'app/shared/model/person.model';
import { IOfferer } from 'app/shared/model/offerer.model';

export interface ILocation {
  id?: number;
  streetAddress?: string;
  streetNumber?: number;
  flatNumber?: number;
  safeZone?: boolean;
  country?: string;
  zipCode?: string;
  city?: string;
  province?: string;
  department?: string;
  coordinateId?: number;
  persons?: IPerson[];
  offerers?: IOfferer[];
}

export class Location implements ILocation {
  constructor(
    public id?: number,
    public streetAddress?: string,
    public streetNumber?: number,
    public flatNumber?: number,
    public safeZone?: boolean,
    public country?: string,
    public zipCode?: string,
    public city?: string,
    public province?: string,
    public department?: string,
    public coordinateId?: number,
    public persons?: IPerson[],
    public offerers?: IOfferer[]
  ) {
    this.safeZone = this.safeZone || false;
  }
}
