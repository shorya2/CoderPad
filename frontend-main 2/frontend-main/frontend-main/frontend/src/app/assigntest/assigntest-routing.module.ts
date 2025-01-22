import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AssigntaComponent } from './assignta/assignta.component';
import { AssignedcComponent } from './assignedc/assignedc.component';
import { CandidateListComponent } from './candidate-list/candidate-list.component';
import { authGuard } from '../services/guard/auth.guard';


const routes: Routes = [
  { path: 'assignta', component: AssigntaComponent , canActivate: [authGuard]},
  { path: 'assignedc', component: AssignedcComponent , canActivate: [authGuard]},
  { path: 'candidate-list', component: CandidateListComponent , canActivate: [authGuard]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AssigntestRoutingModule { }
