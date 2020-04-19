import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {HomepageComponent} from './Components/homepage/homepage.component';
import {DashboardComponent} from './Components/dashboard/dashboard.component';
import {SummaryComponent} from './Components/summary/summary.component';
import {FriendsComponent} from './Components/friends/friends.component';
import {GroupsComponent} from './Components/groups/groups.component';
import {AuthGuardGuard} from './guards/auth-guard.guard';


const routes: Routes = [
  {path: '', component: HomepageComponent},
  {path: 'dashboard',
   component: DashboardComponent,
   canActivateChild: [AuthGuardGuard],
   children: [
     {path: 'summary', component: SummaryComponent},
     {path: '', redirectTo: 'summary', pathMatch: 'full'},
     {path: 'friends/:id', component: FriendsComponent},
     {path: 'groups/:id', component: GroupsComponent}
   ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
