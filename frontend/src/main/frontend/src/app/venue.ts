import { Address } from './address';

export class Venue {
  constructor(
    public id: number,
    public name: string,
    public phone: number,
    public startWork: string,
    public endWork: string,
    public address?: Address) {}
}
