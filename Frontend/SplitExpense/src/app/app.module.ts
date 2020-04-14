import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule} from './app-routing.module';
import { AppComponent } from './app.component';
import { HomepageComponent } from './Components/homepage/homepage.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

// Internal module imports
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';

// Material UI Imports
import {MaterialModule} from './Modules/material/material.module';
import { MessageDialogComponent } from './dialogComponent/message-dialog/message-dialog.component';

// Service Imports
import {LoginSignupService} from './services/login-signup.service';
import { DashboardComponent } from './Components/dashboard/dashboard.component';
import { SummaryComponent } from './Components/summary/summary.component';
import { SideNavigationComponent } from './Components/side-navigation/side-navigation.component';
import { FriendsComponent } from './Components/friends/friends.component';
import { GroupsComponent } from './Components/groups/groups.component';





@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    MessageDialogComponent,
    DashboardComponent,
    SummaryComponent,
    SideNavigationComponent,
    FriendsComponent,
    GroupsComponent
  ],
  entryComponents: [MessageDialogComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule
  ],
  providers: [LoginSignupService],
  bootstrap: [AppComponent]
})
export class AppModule { }

