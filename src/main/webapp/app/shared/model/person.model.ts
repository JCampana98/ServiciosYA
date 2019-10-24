import { Moment } from 'moment';
import { ITurn } from 'app/shared/model/turn.model';
import { IComment } from 'app/shared/model/comment.model';

export const enum Gender {
  MALE = 'MALE',
  FEMALE = 'FEMALE',
  OTHER = 'OTHER'
}

export interface IPerson {
  id?: number;
  gender?: Gender;
  phoneNumber?: number;
  birthday?: Moment;
  turns?: ITurn[];
  comments?: IComment[];
  locationStreetAddress?: string;
  locationId?: number;
}

export class Person implements IPerson {
  constructor(
    public id?: number,
    public gender?: Gender,
    public phoneNumber?: number,
    public birthday?: Moment,
    public turns?: ITurn[],
    public comments?: IComment[],
    public locationStreetAddress?: string,
    public locationId?: number
  ) {}
}
