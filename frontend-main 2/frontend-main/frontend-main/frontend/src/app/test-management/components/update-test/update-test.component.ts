import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TestService } from 'src/app/services/test/test.service';

@Component({
  selector: 'app-update-test',
  templateUrl: './update-test.component.html',
  styleUrls: ['./update-test.component.scss']
})
export class UpdateTestComponent {
  testForm: FormGroup;
  testId: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private testService: TestService, // Inject the service
    private router: Router
  ) {
    this.testForm = this.fb.group({
      createdBy: ['', Validators.required],
      testName: ['', Validators.required],
      testDescription: ['', Validators.required],
      duration: [null, [Validators.required, Validators.min(1)]]
    });
  }

  ngOnInit() {
    // Get the ID from the route parameters
    this.testId = this.route.snapshot.paramMap.get('id');
    // this.testId = "b121";

    if (this.testId) {
      // Fetch the test details using the service
      this.testService.getTestById(this.testId).subscribe({
        next: (test) => {
          // Populate the form with fetched data
          this.testForm.patchValue({
            createdBy: test.createdBy,
            testName: test.testName,
            testDescription: test.testDescription,
            duration: test.duration
          });
        },
        error: (error) => console.error('Error fetching test details:', error)
      });
    }
  }

  updateTest() {
    if (this.testForm.valid && this.testId) {
      const updatedTestData = this.testForm.value;

      // Update using test service
      this.testService.updateTest(this.testId, updatedTestData).subscribe({
        next: () => {
          alert('Test updated successfully!');
          // this.router.navigate(['/test-management']); // Navigate back to a relevant page
        },
        error: (err) => console.error('Error updating test:', err)
      });
    }
  }
}
