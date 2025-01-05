import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';



const routes: Routes = [
  {
    path:'' , loadChildren: ()=> import('./login/login.module').then(m => m.LoginModule)
  },
  {
    path:'' , loadChildren: ()=> import('./test-management/test-management.module').then(m => m.TestManagementModule)
  },
  
  { path: 'roles', 
    loadChildren: () => import('./roles/roles.module').then(m => m.RolesModule) },
  {
    path: 'assigntest',
    loadChildren: () => import('./assigntest/assigntest.module').then(m => m.AssigntestModule)
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
