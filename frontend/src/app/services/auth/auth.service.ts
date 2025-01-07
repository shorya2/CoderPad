import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = 'http://localhost:8090';  // Ensure correct backend URL

  constructor(private http: HttpClient) {}

  // Signup method for registering new user
  signup(userData: any): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/users/register`, userData).pipe(
      tap((response) => {
        // Handle response, e.g., store token if returned
        console.log('Signup response:', response);
      })
    );
  }

  // Login method for user authentication
  login(credentials: { email: string; password: string }): Observable<any> {
    // Prepare the parameters to be sent in the URL
    const params = new HttpParams()
      .set('email', credentials.email)
      .set('password', credentials.password);

    // Send the request with the credentials as query parameters
    return this.http.post<any>(`${this.apiUrl}/users/login`, null, { params }).pipe(
      tap((response) => {
        // Assuming the backend returns a JWT token on successful login
        if (response?.jwtToken) {
          this.setToken(response.jwtToken); // Store the token
        }
      })
    );
  }

  // Google login method (optional, depends on your implementation)
  googleLogin(googleToken: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/google-login`, { token: googleToken }).pipe(
      tap((response) => {
        if (response?.jwtToken) {
          this.setToken(response.jwtToken); // Store token from Google login
        }
      })
    );
  }

  // Logout method: Removes the JWT token
  logout(): void {
    this.removeToken(); // Clear token from storage
  }

  // Save the JWT token to sessionStorage (or localStorage)
  setToken(token: string): void {
    sessionStorage.setItem('auth_token', token); // or use localStorage
  }

  // Get the JWT token
  getToken(): string | null {
    return sessionStorage.getItem('auth_token'); // or use localStorage
  }

  // Remove the JWT token
  removeToken(): void {
    sessionStorage.removeItem('auth_token'); // or localStorage
  }

  // Check if the user is authenticated (i.e., if token exists)
  isAuthenticated(): boolean {
    const token = this.getToken();
    return !!token; // returns true if token exists, otherwise false
  }

  getUserIDFromToken(): number | null {
    const token = this.getToken();
    if (token) {
      const decodedToken: any = this.decodeToken(token);
      return decodedToken?.userID || null; // Replace 'userID' with the correct field name from your token payload
    }
    return null;
  }

  // Decode JWT token (this is useful for extracting claims like userID)
  private decodeToken(token: string): any {
    const payload = token.split('.')[1];
    return JSON.parse(atob(payload));  // Decode the payload (Base64 encoded)
  }
}
