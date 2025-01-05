import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginRoutingModule } from './login-routing.module';
import { LoginComponent } from './components/login/login.component';
import { SignupComponent } from './components/signup/signup.component';
import { ProfileComponent } from './components/profile/profile.component';
import { AdminApprovalComponent } from './components/admin-approval/admin-approval.component';
import { UserListComponent } from './components/user-list/user-list.component';


@NgModule({
  declarations: [
    LoginComponent,
    SignupComponent,
    ProfileComponent,
    AdminApprovalComponent,
    UserListComponent
  ],
  imports: [
    CommonModule,
    LoginRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class LoginModule { }
