// auth.guard.ts

import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service'; // Adjust import according to your folder structure
import { CanActivateFn } from '@angular/router';

export const authGuard: CanActivateFn = () => {
  const authService = inject(AuthService); // Inject AuthService
  const router = inject(Router); // Inject Router

  const token = localStorage.getItem('authToken'); // Get token from localStorage

  if (token) {
    // If the token is found, allow access
    console.log('Authenticated with token:', token); // You can log the token here if needed
    return true;
  } else {
    // If no token, redirect to the login page
    console.log('No token found, redirecting to login');
    router.navigate(['/login']);
    return false;
  }
};
