import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AssigntaComponent } from './assignta/assignta.component';
import { AssignedcComponent } from './assignedc/assignedc.component';
import { CandidateListComponent } from './candidate-list/candidate-list.component';


const routes: Routes = [
  { path: 'assignta', component: AssigntaComponent},
  { path: 'assignedc', component: AssignedcComponent },
  { path: 'candidate-list', component: CandidateListComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AssigntestRoutingModule { }
