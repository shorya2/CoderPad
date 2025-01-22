import { Component } from '@angular/core';
import { TestService } from 'src/app/services/test/test.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-assignta',
  templateUrl: './assignta.component.html',
  styleUrls: ['./assignta.component.scss']
})
export class AssigntaComponent {
  tests: any[] = [];

  constructor(private testService: TestService, private router: Router) {}

  ngOnInit(): void {
    this.fetchTests();
  }

  // Fetch tests from the server
  fetchTests(): void {
    this.testService.getAllTests().subscribe((data) => {
      this.tests = data;
    });
  }
  sendTest(test: any): void {
    this.testService.assignTest(test).subscribe(() => {
      alert('Test "${test.testName}" has been sent!');
    });
  }

  goToCandidates(testId : string){
     this.router.navigate(['/assigntest/candidate-list'],{ queryParams: { id: testId} });
  }

}
