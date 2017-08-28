export class User {
	constructor(
	  public name: string,
	  public email: string,
	  public phone?: number,
	  public roles?: Object[],
	  public id?: number,
	  public bookings?: Object[],
	  public selected?: boolean) {}
}
