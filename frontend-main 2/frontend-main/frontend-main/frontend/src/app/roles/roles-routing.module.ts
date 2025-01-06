
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
const routes: Routes = [
  { path: 'candidates', component: CandidatesComponent },
  { path: 'data', component: DataComponent },
  { path: 'test', component: TestComponent },
  { path: 'system', component: SystemComponent },
  { path: 'assignedc', component: AssignedcComponent },
   // Add route to AssignedcComponent
  { path: 'assignta', component: AssigntaComponent},
  // { path: 'admin-approval', component: AdminApprovalComponent},
  //{ path: 'create-test', component: CreateTestComponent},
  { path: '', redirectTo: '/candidates', pathMatch: 'full' }  // Default route (optional)
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class RolesRoutingModule { }
