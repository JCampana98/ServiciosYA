export interface IComment {
  id?: number;
  qualification?: number;
  commentary?: string;
  offererId?: number;
  personId?: number;
}

export class Comment implements IComment {
  constructor(
    public id?: number,
    public qualification?: number,
    public commentary?: string,
    public offererId?: number,
    public personId?: number
  ) {}
}
