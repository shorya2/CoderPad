import { Component } from '@angular/core';

@Component({
  selector: 'app-candidates',
  templateUrl: './candidates.component.html',
  styleUrls: ['./candidates.component.scss']
})
export class CandidatesComponent {
  showTestButton = false; // Control visibility of the popup/button
  router: any;

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
    // Perform logout logic, e.g., clear session storage
    console.log('Logging out');
    
  }
  logTestMessage() {
    console.log('Test action performed!');
    this.showTestButton = false; // Optionally hide the popup/button
  }
}
