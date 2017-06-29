import { Venue } from './venue';
import { User } from './user';

export class Booking {
  constructor(
    public id: number,
    public startDateTime: string,
    public endDateTime: string,
    public venue?: Venue,
    public customer_id?: number,
    public selected?: boolean) {}
}
