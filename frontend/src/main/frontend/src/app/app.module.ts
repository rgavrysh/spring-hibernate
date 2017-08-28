import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { HttpModule } from '@angular/http';

import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { VenuesComponent } from './venues/venues.component';
import { ProfileComponent } from './profile/profile.component';
import { MyBookingsComponent } from './my-bookings/my-bookings.component';
import { AdminComponent } from './admin/admin.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { LoginComponent } from './login/login.component';
import { BackendService } from './backend.service';
import { AuthService } from './auth.service';
import { AuthGuard } from './auth.guard';
import { APP_SETTINGS, AppSettings } from './app.settings';

const appRoutes: Routes = [
  { path: 'home', component: HomeComponent, canActivate: [ AuthGuard ] },
  { path: 'venues', component: VenuesComponent, canActivate: [ AuthGuard ] },
  { path: 'me', component: ProfileComponent, canActivate: [ AuthGuard ] },
  { path: 'mybookings', component: MyBookingsComponent, canActivate: [ AuthGuard ] },
  { path: 'login', component: LoginComponent },
  { path: 'logout', redirectTo: '/login' },
  { path: 'admin', component: AdminComponent, canActivate: [ AuthGuard ]  },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', component: PageNotFoundComponent }
]

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    VenuesComponent,
    ProfileComponent,
    MyBookingsComponent,
    AdminComponent,
    PageNotFoundComponent,
    LoginComponent
  ],
  imports: [
    RouterModule.forRoot(appRoutes),
    BrowserModule,
    HttpModule,
    FormsModule
  ],
  providers: [ AuthService, BackendService, AuthGuard, {provide: APP_SETTINGS, useValue: AppSettings} ],
  bootstrap: [AppComponent]
})
export class AppModule { }
