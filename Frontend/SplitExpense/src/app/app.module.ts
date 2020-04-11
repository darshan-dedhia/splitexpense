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

// Service Imports
import {LoginSignupService} from './services/login-signup.service';



@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent
  ],
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

