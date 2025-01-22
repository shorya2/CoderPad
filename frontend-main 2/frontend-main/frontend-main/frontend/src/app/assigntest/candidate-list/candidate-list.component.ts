import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { tap, catchError, forkJoin } from 'rxjs';
import { TestService } from 'src/app/services/test/test.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-candidate-list',
  templateUrl: './candidate-list.component.html',
  styleUrls: ['./candidate-list.component.scss'],
})
export class CandidateListComponent implements OnInit {
  candidates: any [] = [];
  selectedCandidates: string[] = [];
  testId : string | null = null; // Replace with dynamic test ID
  testDetails: any = null;
  emailId : string = '';
  isLoading: boolean | undefined;
  errorMessage: string | undefined;
  searchQuery: string = '';
  allCandidates: any [] =[];
  filteredCandidates: any[]= [];
  constructor(
    private userService: UserService,
    private testService: TestService,
    private route : ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.fetchCandidates();
    this.getTestIdFromRoute();
  }

  // Fetch all candidates
  fetchCandidates(): void {
    this.userService.getAllCandidates().subscribe({
      next: (data) => {
        this.filteredCandidates = data;
        this.allCandidates = data;
        this.candidates = [...this.filteredCandidates]; // Adjust to match your API response
      },
      error: (error: any) => console.error('Error fetching candidates:', error)
    });
  }
  // Get Test ID from Route
  getTestIdFromRoute(): void {
    this.route.queryParamMap.subscribe((params) => {
      this.testId = params.get('id');
      if (this.testId) {
        this.fetchTestDetails(this.testId);
      }
    });
  }
  
  fetchTestDetails(testId: string): void {
    this.testService.getTestById(testId).subscribe({
      next: (data) => {
        this.testDetails = data; // Store the fetched test details
        console.log('Fetched test details:', this.testDetails);
      },
      error: (error: any) => console.error('Error fetching test details:', error),
    });
  }


// Search list of candidates based on username and email
  searchCandidates(): void {
    if(!this.searchQuery.trim()){
      this.candidates = this.allCandidates;
    }
    const query = this.searchQuery.toLowerCase();
        this.filteredCandidates = this.candidates.filter((candidate) =>
        candidate.userName.toLowerCase().includes(query) ||
        candidate.email.toLowerCase().includes(query)
      );
      this.candidates = [...this.filteredCandidates];
  }

  // Add candidate to selected list
  selectCandidate(useremail: string): void {
    if (!this.selectedCandidates.includes(useremail)) {
      this.selectedCandidates.push(useremail);
    }
  }

  // Assign test to selected candidates
  assignTestToSelectedCandidates(): void {
    if (this.selectedCandidates.length === 0) {
      alert('Please select candidates to assign the test.');
      return;
    }
 
    this.isLoading = true; // Show loading state
    this.errorMessage = ''; // Reset error message
 
    const assignments : any = [];
    this.selectedCandidates.forEach((email) => {
        assignments.push({
            email: email,
            testId : this.testId,
            createdBy: this.testDetails.createdBy,
            testName: this.testDetails.testName,
            testDescription: this.testDetails.testDescription,
            duration: this.testDetails.duration 
        });
    });
    

    console.log(assignments);

    this.testService.assignTest(assignments).subscribe({
      next: () => {
        alert('Test assigned successfully to selected candidates.');
        this.selectedCandidates = []; // Clear the selection
        this.isLoading = false; // Hide loading state
        this.router.navigate(['/assignta']); // Navigate to another route if needed
      },
      error: (error) => {
        this.isLoading = false; // Hide loading state
        console.error('Error assigning test(s):', error);
        this.errorMessage = 'Failed to assign tests. Please try again.';
      },
    });
    // // Execute the assignments in parallel and handle success/failure
    // forkJoin(assignments).subscribe({
    //   next: () => {
    //     alert('Test assigned successfully to all selected candidates');
    //     this.selectedCandidates = []; // Clear the selection
    //     this.isLoading = false; // Hide loading state
    //     this.router.navigate(['/assignta']); // Navigate to another route if needed
    //   },
    //   error: (error) => {
    //     this.isLoading = false; // Hide loading state
    //     console.error('Error assigning test(s):', error);
    //   }
    // });
  }


  sendTestEmailToCandidate(email: string): void {
    this.testService.sendTestEmail(email).subscribe({
      next: (response) => {
        console.log('Email sent successfully:', response);
        alert(response.message || 'Email sent successfully'); // Using the message from the response
      },
      error: (error) => {
        console.error('Error sending email:', error);
        alert('Error sending test email to ' + email);
      }
    });
  }

}

