import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AssigntestRoutingModule } from './assigntest-routing.module';
import { AssigntaComponent } from './assignta/assignta.component';
import { AssignedcComponent } from './assignedc/assignedc.component';


@NgModule({
  declarations: [
    AssigntaComponent,
    AssignedcComponent
  ],
  imports: [
    CommonModule,
    AssigntestRoutingModule
  ]
})
export class AssigntestModule { }
