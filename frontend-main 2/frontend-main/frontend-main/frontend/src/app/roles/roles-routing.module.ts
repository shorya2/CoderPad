
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CandidatesComponent } from './candidates/candidates.component';
import { DataComponent } from './data/data.component';
import { TestComponent } from './test/test.component';
import { SystemComponent } from './system/system.component';
// import { AttemptedTestsComponent } from './attempted-tests/attempted-tests.component'; // Will create later
// import { ProfileComponent } from './profile/profile.component'; 
import { AssignedcComponent } from '../assigntest/assignedc/assignedc.component';
import { AssigntaComponent } from '../assigntest/assignta/assignta.component';
import { CreateTestComponent } from '../test-management/components/create-test/create-test.component';
import { AdminApprovalComponent } from '../login/components/admin-approval/admin-approval.component';
import { authGuard } from '../services/guard/auth.guard';
const routes: Routes = [
  { path: 'candidates', component: CandidatesComponent, canActivate: [authGuard] },
  { path: 'data', component: DataComponent,  canActivate: [authGuard] },
  { path: 'test', component: TestComponent, canActivate: [authGuard] },
  { path: 'system', component: SystemComponent, canActivate: [authGuard] },
  { path: 'assignedc', component: AssignedcComponent, canActivate: [authGuard] },
   // Add route to AssignedcComponent
  { path: 'assignta', component: AssigntaComponent, canActivate: [authGuard]},
  // { path: 'admin-approval', component: AdminApprovalComponent},
  //{ path: 'create-test', component: CreateTestComponent},
  { path: '', redirectTo: '/login', pathMatch: 'full' }  // Default route (optional)
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RolesRoutingModule { }
