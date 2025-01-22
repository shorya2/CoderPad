import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TestService } from 'src/app/services/test/test.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-assignedc',
  templateUrl: './assignedc.component.html',
  styleUrls: ['./assignedc.component.scss']
})
export class AssignedcComponent {
  assignedTests: any[] = [];
  id: number|null = null;
  email: string ='';
  constructor(
    private testService: TestService, 
    private router: Router,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    // Fetch all assigned tests for the current user
    this.id = parseInt(localStorage.getItem('userId') || 'null');
    // console.log(this.id + " Is Fetched");
    this.userService.getProfile(this.id).subscribe((value:any) => {
        this.email = value.email;
        console.log(this.email);
        this.getAssignedTests();
    });
    
  }

  getAssignedTests(){
    console.log(this.email);
    this.testService.getAssignedTestsByEmail(this.email).subscribe(
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
