import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TestService } from 'src/app/services/test/test.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-test-list',
  templateUrl: './test-list.component.html',
  styleUrls: ['./test-list.component.scss']
})
export class TestListComponent {
  tests: any[] = [];
  adminIds: any[] = ['9440'];
  users: any[] = [];

  constructor(private testService: TestService, private router: Router,private userService:UserService) {}

  ngOnInit() {
    
    // Fetch all tests
    this.testService.getAllTests().subscribe({
      next: (tests) => {
        this.tests = tests;
        this.adminIds = tests.map((test) => test.createdBy);
        
        this.fetchUsersByIds(this.adminIds);
      },
      error: (error) =>{
        console.error('Error fetching tests:', error)
      } 
    });
  }

  // Fetch users by IDs
  fetchUsersByIds(adminIds: string[]) {
    this.userService.getUsersByIds(adminIds).subscribe({
      next: (users) => {
        this.users = users;

        // // Map `createdBy` in tests to the corresponding username
        // this.tests.forEach((test) => {
        //   const user = this.users.find((u) => u.id === test.createdBy);
        //   test.createdByName = user ? user.username : 'Unknown';
        // });
      },
      error: (error) => {
        console.error('Error fetching users:', error);
      }
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
