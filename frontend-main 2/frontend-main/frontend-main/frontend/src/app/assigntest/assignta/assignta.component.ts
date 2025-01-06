import { Component } from '@angular/core';
import { TestService } from 'src/app/services/test/test.service';

@Component({
  selector: 'app-assignta',
  templateUrl: './assignta.component.html',
  styleUrls: ['./assignta.component.scss']
})
export class AssigntaComponent {
  // tests = [
  //   { description: 'Math Test' },
  //   { description: 'Science Test' },
  //   { description: 'History Test' },
  // ];
  tests: any[] = [];

  constructor(private testService: TestService) {}

  // sendTest(test: any) {
  //   console.log(`Sending test: ${test.description}`);
  //   alert(`Test "${test.description}" has been sent!`);
  // }
  // ngOnInit(): void {
  //   console.log('AssignTaComponent loaded!');
  // }

  ngOnInit(): void {
    this.fetchTests();
  }

  // Fetch tests from the JSON server
  fetchTests(): void {
    this.testService.getAllTests().subscribe((data) => {
      this.tests = data;
    });
  }

  // Assign a test to /assignedTests
  sendTest(test: any): void {
    this.testService.assignTest(test).subscribe(() => {
      alert(`Test "${test.testName}" has been sent!`);
    });
  }
}
