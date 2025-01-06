import { Component } from '@angular/core';
import { TestService } from 'src/app/services/test/test.service';

@Component({
  selector: 'app-finished-test',
  templateUrl: './finished-test.component.html',
  styleUrls: ['./finished-test.component.scss']
})
export class FinishedTestComponent {
  AttemptedTest: any[] = [];

  constructor(private testService: TestService) {}

  ngOnInit(): void {
    this.loadAttemptedTests();
  }
  logout(): void {
    // Perform logout logic, e.g., clear session storage
    console.log('Logging out');
    
  }

  isDropdownVisible: boolean = false;
  // logMessage() {
  //   console.log('Candidates page is working!');
  // }
  toggleDropdown(): void {
    this.isDropdownVisible = !this.isDropdownVisible;
  }
  // Fetch attempted tests from the server
  loadAttemptedTests(): void {
    this.testService.getAttemptedTests().subscribe((tests) => {
      this.AttemptedTest= tests;
    });

  }
}
