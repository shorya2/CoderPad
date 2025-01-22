import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { TestService } from 'src/app/services/test/test.service';

@Component({
  selector: 'app-otp-login',
  templateUrl: './otp-login.component.html',
  styleUrls: ['./otp-login.component.scss']
})
export class OtpLoginComponent {
  otpLoginForm!: FormGroup;
  OtpService: any;

  constructor(private fb: FormBuilder, private router: Router, private testService: TestService) {}

  ngOnInit(): void {
    this.initializeForm();
  }

  // Initialize the form with validators
  initializeForm(): void {
    this.otpLoginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]], // Email must be required and valid
      otp: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(6)]] // OTP must be required with length constraints
    });
  }

  // onVerify(): void {
  //   if (this.otpLoginForm.valid) {
  //     console.log('Form Values:', this.otpLoginForm.value);
  //     // this.router.navigate(['/candidate-dashboard']);
  //   } else {
  //     alert('Please provide valid email and OTP!');
  //   }
  // }


  onVerify(): void {
    if (this.otpLoginForm.valid) {
      const { email, otp } = this.otpLoginForm.value;
      console.log('User OTP: ', otp);  // Log OTP entered by user
 
      // Call the backend to verify the OTP
      this. testService.verifyOtp({ email, otp }).subscribe({
        next: (response: any) => {
          console.log('OTP verification response:', response);
 
          // Store user email in localStorage for later use
          localStorage.setItem('email', email);
 
          // Redirect to the assigned tests page or dashboard
          this.router.navigate([`/assignedc`]);  // Make sure '/assignedc' is the correct route for showing assigned tests
        },
        error: (error: any) => {
          console.error('OTP verification failed:', error);
          alert('Invalid OTP or email!');
        }
      });
    } else {
      alert('Please provide valid email and OTP!');
    }
  }

  // Getter for form controls
  get f() {
    return this.otpLoginForm.controls;
  }
}
