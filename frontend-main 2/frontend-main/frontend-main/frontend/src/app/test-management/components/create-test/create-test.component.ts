import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { TestService } from 'src/app/services/test/test.service';

@Component({
  selector: 'app-create-test',
  templateUrl: './create-test.component.html',
  styleUrls: ['./create-test.component.scss']
})
export class CreateTestComponent {
  testForm: FormGroup;

  constructor(private fb: FormBuilder, private http: HttpClient,private router:Router,private testService:TestService) {
    this.testForm = this.fb.group({
      createdBy: ['', Validators.required],
      testName: ['', Validators.required],
      testDescription: ['', Validators.required],
      duration: [null, [Validators.required, Validators.min(1)]]
    });
  }

  saveTest() {
    if (this.testForm.valid) {
      const testData = this.testForm.value;

      // Call the service to create the test
      this.testService.createTest(testData).subscribe({
        next: () => {
          alert('Test created successfully!');
          this.testForm.reset(); // Reset the form
        },
        error: (error) => console.error('Error creating test:', error)
      });
    }
  }
}
