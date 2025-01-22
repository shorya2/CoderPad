import { Component } from '@angular/core';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-candidates',
  templateUrl: './candidates.component.html',
  styleUrls: ['./candidates.component.scss']
})
export class CandidatesComponent {
  showTestButton = false; // Control visibility of the popup/button
  router: any;
  userName: string | null = null;
  id : number|null = null;

  constructor(private userService: UserService){}

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
  

  // Trigger popup/button display
  showPopup() {
    this.showTestButton = true;
  }
  isDropdownVisible: boolean = false;
  // logMessage() {
  //   console.log('Candidates page is working!');
  // }
  toggleDropdown(): void {
    this.isDropdownVisible = !this.isDropdownVisible;
  }

  logout(): void {
    console.log('Logging out');
 
    localStorage.removeItem('authToken');
    localStorage.removeItem('userId');
    localStorage.removeItem('userName');
 
    this.router.navigate(['/login']);
  }
  
  logTestMessage() {
    console.log('Test action performed!');
    this.showTestButton = false; // Optionally hide the popup/button
  }
}
