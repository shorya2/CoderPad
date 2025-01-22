import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { tap } from 'rxjs/operators';
import { AuthService } from 'src/app/services/auth.service';
import { Oauth2Service } from 'src/app/services/oauth2.service';  // Import Oauth2Service

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  email: string = '';
  password: string = '';
  errorMessage: string = '';
  loginForm: FormGroup = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]]
  });

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private oauth2Service: Oauth2Service,
    private router: Router
  ) {}

  ngOnInit() {}

  login() {
    const credentials = {
      email: this.email,
      password: this.password
    };

    this.authService.login(credentials)
      .pipe(
        tap(response => {
          console.log('Login response:', response);
        })
      ).subscribe({
        next: (response: any) => {
          // Store the JWT token and role in localStorage
          localStorage.setItem('authToken', response.jwtToken);
          localStorage.setItem('userRole', response.role);
          localStorage.setItem('userId', response.id.toString());
          localStorage.setItem('userEmail', response.email);

          // Redirect based on the role
          this.redirectBasedOnRole(response.role);
        },
        error: (error) => {
          console.error('Login failed:', error);
          alert('Login failed!');
        }
      });
  }

  loginWithGoogle(): void {
    this.oauth2Service.login();

    // Check if the user is logged in
    const googleToken = this.oauth2Service.accessToken;
    if (googleToken) {
      // Send the token to your backend for validation
      this.authService.googleLogin(googleToken).subscribe({
        next: (response: any) => {
          // Store JWT token and role after successful Google login
          localStorage.setItem('authToken', response.jwtToken);
          localStorage.setItem('userRole', response.role);

          // Redirect based on the role
          this.redirectBasedOnRole(response.role);
        },
        error: (error: any) => {
          console.error('Google login failed:', error);
          alert('Google login failed!');
        }
      });
    }
  }

  private redirectBasedOnRole(role: string): void {
    if (role === 'candidate') {
      this.router.navigate(['/candidates']);
    } else if (role === 'test-admin') {
      this.router.navigate(['/test']);
    } else if (role === 'data-admin') {
      this.router.navigate(['/data']);
    }
    else if (role === 'System Admin') {
      this.router.navigate(['/system']);
    }else {
      this.router.navigate(['/login']); // Fallback in case of an unknown role
    }
  }
}
