import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule} from './app-routing.module';
import { AppComponent } from './app.component';
import { HomepageComponent } from './Components/homepage/homepage.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

// Internal module imports
import {HttpClientModule, HTTP_INTERCEPTORS} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

// Material UI Imports
import {MaterialModule} from './Modules/material/material.module';
import { MessageDialogComponent } from './dialogComponent/message-dialog/message-dialog.component';
import { AddModifyTransactionsComponent } from './Components/add-modify-transactions/add-modify-transactions.component';

// Service Imports
import {LoginSignupService} from './services/login-signup.service';
import { DashboardComponent } from './Components/dashboard/dashboard.component';
import { SummaryComponent } from './Components/summary/summary.component';
import { SideNavigationComponent } from './Components/side-navigation/side-navigation.component';
import { FriendsComponent } from './Components/friends/friends.component';
import { GroupsComponent } from './Components/groups/groups.component';
import { TokenInterceptorService } from './services/token-interceptor.service';

//Guard imports
import {AuthGuardGuard} from './guards/auth-guard.guard';
import {UserServiceService} from './services/user-service.service';
import {SelectedTransactionService} from './services/selected-transaction.service';
import { AbsolutePipe } from './pipes/absolute.pipe';
import { YourSharePipe } from './pipes/your-share.pipe';
import { YesNoDialogComponent } from './dialogComponent/yes-no-dialog/yes-no-dialog.component';
import { SettleUpComponent } from './Components/settle-up/settle-up.component';
import {MatSelectModule} from '@angular/material/select';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import { AddModifyGroupComponent } from './Components/add-modify-group/add-modify-group.component';
import { AddFriendComponent } from './Components/add-friend/add-friend.component';
import { UserPipe } from './pipes/user.pipe';
import { ViewGroupBalanceComponent } from './Components/view-group-balance/view-group-balance.component';









@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    MessageDialogComponent,
    DashboardComponent,
    SummaryComponent,
    SideNavigationComponent,
    FriendsComponent,
    GroupsComponent,
    AddModifyTransactionsComponent,
    AbsolutePipe,
    YourSharePipe,
    YesNoDialogComponent,
    SettleUpComponent,
    AddModifyGroupComponent,
    AddFriendComponent,
    UserPipe,
    ViewGroupBalanceComponent
  ],
  entryComponents: [MessageDialogComponent,
                   AddModifyTransactionsComponent,
                   YesNoDialogComponent,
                   SettleUpComponent,
                   AddModifyGroupComponent,
                   AddFriendComponent,
                   ViewGroupBalanceComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    MaterialModule,
    FormsModule,
    ReactiveFormsModule,
    MatSelectModule,
    MatAutocompleteModule
  ],
  providers: [LoginSignupService,
              AuthGuardGuard,
              UserServiceService,
              SelectedTransactionService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptorService,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }

