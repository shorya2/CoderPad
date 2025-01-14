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
  isLoading: boolean | undefined;
  errorMessage: string | undefined;
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
        this.candidates = data; // Adjust to match your API response
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
  // Add candidate to selected list
  selectCandidate(useremail: string): void {
    if (!this.selectedCandidates.includes(useremail)) {
      this.selectedCandidates.push(useremail);
    }
  }

  // Assign test to selected candidates
  // assignTestToSelectedCandidates(): void {
  //   const assignments = this.selectedCandidates.map((useremail) => {
  //     const assignCandidate = {
  //       email : useremail,
  //       ...this.testDetails
  //     }
  //     return assignCandidate;
  //   });

  //   this.testService.assignTest(assignments).subscribe({
  //     next:() => {
  //       console.log('Test assigned successfully to selected candidates');
  //       alert('Test assigned successfully');
  //       this.selectedCandidates = []; // Clear the selection
  //       this.router.navigate(['/assignta']);

  //     },
  //     error:(error) => console.error('Error assigning test:', error)
  //   });
  // }

  
 
  assignTestToSelectedCandidates(): void {
    if (this.selectedCandidates.length === 0) {
      alert('Please select candidates to assign the test.');
      return;
    }
 
    this.isLoading = true; // Show loading state
    this.errorMessage = ''; // Reset error message
 
    const assignments = this.selectedCandidates.map((email) => {
      const assignCandidate = {
        email: email,
        // testId: this.testDetails.testId,  // Assuming testId is available
        // testName: this.testDetails.testName,
        // testDescription: this.testDetails.testDescription,
        // createdBy: this.testDetails.createdBy,  // Adjust based on your actual logic
        // duration: this.testDetails.duration,
        // questions: this.testDetails.questions
        ...this.testDetails
      };
 
      console.log('Assigning test to candidate:', assignCandidate);
 
      // Call the backend to send the email with OTP
      return this.testService.assignTest(assignCandidate).pipe(
        tap((response) => {
          console.log('Test assigned successfully:', response);
          this.sendTestEmailToCandidate(email);  // Send email to the candidate after assigning the test
        }),
        catchError((error) => {
          console.error('Error assigning test:', error);
          this.errorMessage = 'Error assigning test to one or more candidates.';
          this.isLoading = false; // Hide loading state
          alert(this.errorMessage);  // Show error alert
          throw error;  // Propagate the error if needed
        })
      );
    });
 
    // Execute the assignments in parallel and handle success/failure
    forkJoin(assignments).subscribe({
      next: () => {
        alert('Test assigned successfully to all selected candidates');
        this.selectedCandidates = []; // Clear the selection
        this.isLoading = false; // Hide loading state
        this.router.navigate(['/assignta']); // Navigate to another route if needed
      },
      error: (error) => {
        this.isLoading = false; // Hide loading state
        console.error('Error assigning test(s):', error);
      }
    });
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

