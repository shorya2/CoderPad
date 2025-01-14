import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TestService } from 'src/app/services/test/test.service';

@Component({
  selector: 'app-assignedc',
  templateUrl: './assignedc.component.html',
  styleUrls: ['./assignedc.component.scss']
})
export class AssignedcComponent {
  assignedTests: any[] = [];

  constructor(private testService: TestService, private router: Router) {}

  ngOnInit(): void {
    // Fetch all assigned tests for the current user
    this.testService.getAssignedTests().subscribe(
      (data) => {
        this.assignedTests = data;
        console.log('Assigned Tests:', this.assignedTests);
      },
      (error) => {
        console.error('Error fetching assigned tests:', error);
      }
    );
  }

  // Start the test by navigating to the test page
  startTest(test: any): void {
    console.log('Starting test:', test);
    this.router.navigate([`/attempt-test/${test.id}`]);
  }
}
