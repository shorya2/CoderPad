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
  useremail: string = '';
  password: string = '';
  errorMessage: string = '';
  loginForm: FormGroup = this.fb.group({
    useremail: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]]
  });

  constructor(private fb: FormBuilder, private authService: AuthService, private oauth2Service: Oauth2Service, private router: Router) {}

  ngOnInit() {}

  login() {
    const credentials = {
      useremail: this.useremail,
      password: this.password
    };

    this.authService.login(credentials)
      .pipe(
        tap(response => {
          console.log('Login response:', response);
        })
      ).subscribe({
        next: (response: any) => {
          localStorage.setItem('authToken', response.token);
          this.router.navigate(['/dashboard']);
        },
        error: (error) => {
          console.error('Login failed:', error);
          alert('Login failed!');
        }
      });
  }

  loginWithGoogle(): void {
    this.oauth2Service.login();
  }
}
