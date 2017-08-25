import { OpaqueToken } from '@angular/core';

export let APP_SETTINGS = new OpaqueToken('app.settings');

export interface IAppSettings {
  apiProtocol : string;
  apiHost : string;
  apiPort : string;
  securityClientName : string;
  securityClientSecret : string;
}

export const AppSettings: IAppSettings = {
  apiProtocol: 'http',
  apiHost: 'localhost',
  apiPort: '8080',
  securityClientName: 'rest',
  securityClientSecret: 'qwe123'
};
