import { Moment } from 'moment';

export const enum TurnState {
  ONWAY = 'ONWAY',
  DELAY = 'DELAY',
  ARRIVED = 'ARRIVED',
  CANCELED = 'CANCELED',
  FINISHED = 'FINISHED'
}

export interface ITurn {
  id?: number;
  orderDate?: Moment;
  workDate?: Moment;
  cost?: number;
  description?: any;
  available?: boolean;
  turnState?: TurnState;
  transactionId?: number;
  locationId?: number;
  offererId?: number;
  personId?: number;
}

export class Turn implements ITurn {
  constructor(
    public id?: number,
    public orderDate?: Moment,
    public workDate?: Moment,
    public cost?: number,
    public description?: any,
    public available?: boolean,
    public turnState?: TurnState,
    public transactionId?: number,
    public locationId?: number,
    public offererId?: number,
    public personId?: number
  ) {
    this.available = this.available || false;
  }
}
