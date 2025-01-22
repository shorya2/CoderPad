import { Component } from '@angular/core';
import { UserService } from 'src/app/services/user/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-system',
  templateUrl: './system.component.html',
  styleUrls: ['./system.component.scss']
})
export class SystemComponent {
  userName: string | null = null;
  // router: any;
    id : number|null = null;
  
    constructor(private userService: UserService,private router:Router){}
  
    ngOnInit(): void {
      this.id = parseInt(localStorage.getItem('userId') || 'null');
      this.getUserName();
    }
  
  
    getUserName(): void {
      console.log(this.id);
      if (this.id !== null) {
        this.userService.getProfile(this.id).subscribe((value:any)=>{
          this.userName = value.userName;
        });
      } else {
        console.warn('User ID is null, unable to fetch profile');
      }
    }

    logout(): void {
      console.log('Logging out');
   
      localStorage.removeItem('authToken');
      localStorage.removeItem('userId');
      localStorage.removeItem('userName');
   
      this.router.navigate(['/login']);
    }
}
