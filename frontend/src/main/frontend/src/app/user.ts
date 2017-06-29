export class User {
	constructor(
	  public name: string,
	  public phone: number,
	  public email: string,
	  public roles?: Object[],
	  public id?: number,
	  public bookings?: Object[],
	  public selected?: boolean) {}
}
