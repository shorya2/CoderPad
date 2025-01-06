import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TestService } from 'src/app/services/test/test.service';

@Component({
  selector: 'app-assignedc',
  templateUrl: './assignedc.component.html',
  styleUrls: ['./assignedc.component.scss']
})
export class AssignedcComponent {
  // tests = [
  //   { sNo: 1, description: 'Math Test', duration: '60 mins' },
  //   { sNo: 2, description: 'Science Test', duration: '45 mins' },
  //   { sNo: 3, description: 'History Test', duration: '30 mins' },
  // ];
  assignedTests: any[] = [];
  constructor(private testService: TestService,private router: Router) {}

  ngOnInit(): void {
    // Fetch all assigned tests
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

  startTest(test: any): void {
    console.log('Starting test:', test);
    // Add logic to handle test start, e.g., navigate to the test page
     this.router.navigate([`/attempt-test/${test.id}`]);
  }
}
