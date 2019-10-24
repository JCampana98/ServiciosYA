export interface IProfession {
  id?: number;
  name?: string;
  professionDetail?: string;
  offererId?: number;
}

export class Profession implements IProfession {
  constructor(public id?: number, public name?: string, public professionDetail?: string, public offererId?: number) {}
}
