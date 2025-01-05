import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RolesRoutingModule } from './roles-routing.module';
import { CandidatesComponent } from './candidates/candidates.component';
import { DataComponent } from './data/data.component';
import { TestComponent } from './test/test.component';
import { SystemComponent } from './system/system.component';


@NgModule({
  declarations: [
    CandidatesComponent,
    DataComponent,
    TestComponent,
    SystemComponent
  ],
  imports: [
    CommonModule,
    RolesRoutingModule,
 
  ]
})
export class RolesModule { }
