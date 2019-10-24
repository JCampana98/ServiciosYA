import { Moment } from 'moment';
import { IProfession } from 'app/shared/model/profession.model';
import { ITurn } from 'app/shared/model/turn.model';
import { IComment } from 'app/shared/model/comment.model';

export const enum Gender {
  MALE = 'MALE',
  FEMALE = 'FEMALE',
  OTHER = 'OTHER'
}

export interface IOfferer {
  id?: number;
  gender?: Gender;
  phoneNumber?: number;
  birthday?: Moment;
  reputation?: number;
  professions?: IProfession[];
  turns?: ITurn[];
  comments?: IComment[];
  locationStreetAddress?: string;
  locationId?: number;
}

export class Offerer implements IOfferer {
  constructor(
    public id?: number,
    public gender?: Gender,
    public phoneNumber?: number,
    public birthday?: Moment,
    public reputation?: number,
    public professions?: IProfession[],
    public turns?: ITurn[],
    public comments?: IComment[],
    public locationStreetAddress?: string,
    public locationId?: number
  ) {}
}
