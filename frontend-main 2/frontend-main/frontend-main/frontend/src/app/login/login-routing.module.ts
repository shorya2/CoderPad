import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { AdminApprovalComponent } from './components/admin-approval/admin-approval.component';
import { UserListComponent } from './components/user-list/user-list.component';
import { ProfileComponent } from './components/profile/profile.component';
import { OtpLoginComponent } from './components/otp-login/otp-login.component';
import { authGuard } from '../services/guard/auth.guard';


const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'users', component: UserListComponent , canActivate: [authGuard]},
  { path: 'profile', component: ProfileComponent , canActivate: [authGuard]},
  { path: 'admin-approval', component: AdminApprovalComponent , canActivate: [authGuard] },
  { path:'otp-login', component:OtpLoginComponent , canActivate: [authGuard]},
  // { path: '', redirectTo: 'login', pathMatch: 'full' },
  {path:'', redirectTo:'login',pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class LoginRoutingModule { }
