import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TestService } from 'src/app/services/test/test.service';

@Component({
  selector: 'app-test-list',
  templateUrl: './test-list.component.html',
  styleUrls: ['./test-list.component.scss']
})
export class TestListComponent {
  tests: any[] = [];

  constructor(private testService: TestService, private router: Router) {}

  ngOnInit() {
    // Fetch all tests
    this.testService.getAllTests().subscribe({
      next: (tests) => {
        this.tests = tests;
      },
      error: (error) => console.error('Error fetching tests:', error)
    });
  }

  // Navigate to the update test component
  updateTest(id: string) {
    this.router.navigate([`./update-test/${id}`]);
  }
  viewTestDetails(testId: string): void {
    this.router.navigate(['/test-questions', testId]);
  }
}
