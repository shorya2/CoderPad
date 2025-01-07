import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss'],
})
export class SignupComponent {
  username: string = '';
  password: string = '';
  useremail: string = '';
  cnfpassword: string = '';
  userrole: string = '';

  constructor(private router: Router, private authService: AuthService) {}

  signup() {
    // Check if the passwords match
    if (this.password !== this.cnfpassword) {
      alert('Passwords do not match!');
      return;
    }

    // Format the user data correctly
    this.authService.signup({
      userName: this.username,
      email: this.useremail,
      password: this.password,
      roleName: this.userrole
    }).subscribe({
      next: (response) => {
        console.log(response);
        alert('Signup successful!');
        this.router.navigate(['/login']);
      },
      error: (error) => {
        console.error('Signup failed:', error);
        alert('Signup failed!');
      }
    });
  }
}

