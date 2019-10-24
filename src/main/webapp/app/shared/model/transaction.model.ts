import { Moment } from 'moment';

export interface ITransaction {
  id?: number;
  date?: Moment;
  amount?: number;
  turnId?: number;
}

export class Transaction implements ITransaction {
  constructor(public id?: number, public date?: Moment, public amount?: number, public turnId?: number) {}
}
