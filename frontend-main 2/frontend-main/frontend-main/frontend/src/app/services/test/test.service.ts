import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TestService {
  // private baseUrl = 'http://localhost:3000/tests';
  private mockUrl = 'http://localhost:3000';
  // private attmptUrl = 'http://localhost:3000/AttemptedTest';

  private baseUrl = 'http://localhost:8081/api/tests';
  private url = 'http://localhost:8081';
  private attmptUrl = 'http://localhost:8081/api/attempted-tests';

  constructor(private http: HttpClient) {}

  //Fetch all tests
  getAllTests(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl);
  }

  // getAllUsers(): Observable<any[]> {
  //   return this.http.get<any[]>(`${this.url}/api/users`);
  // }

  // Fetch a test by ID
  getTestById(id: string): Observable<any> {
    return this.http.get(`${this.baseUrl}/${id}`);
  }

  // Update a test by ID
  updateTest(id: string, testData: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/${id}`, testData);
  }

  // Create a new test
  createTest(testData: any): Observable<any> {
    return this.http.post(this.baseUrl, testData);
  }

   // Add a test to assignedTests
  assignTest(test: any): Observable<any> {
    return this.http.post<any>(`${this.url}/api/assigned-tests`, test);
    // return this.http.post<any>(`${this.mockUrl}/assignedTests`,test);
  }

  // Fetch all assigned tests from the server
  getAssignedTests(): Observable<any[]> {
    return this.http.get<any[]>(`${this.url}/api/assigned-tests`);
  }

  // Attempted Test Addition
  submitAttemptedTest(attemptedTest: any): Observable<any> {
    return this.http.post(this.attmptUrl, attemptedTest);
  }

  // // Fetch finished tests
  // getFinishedTests(): Observable<any[]> {
  //   return this.http.get<any[]>(this.attmptUrl);
  // }

   // Remove test from assigned tests
   removeAssignedTest(email: string,createdBy: string): Observable<void> {
    return this.http.delete<void>(`${this.url}/api/assigned-tests/by-email-and-createdBy?email=${email}&createdBy=${createdBy}`);
  }

  getAttemptedTests(): Observable<any[]> {
    return this.http.get<any[]>(this.attmptUrl);
  }

  sendTestEmail(userEmail: string): Observable<any> {
    return this.http.get(`${this.url}/api/send-email?userEmail=${userEmail}`);
  }

  verifyOtp(otpData :{email: string, otp: string} ): Observable<any> {
    console.log(otpData);
    return this.http.post(`${this.url}/otp/verify-otp`,otpData);
  }
 
  getAssignedTestsByEmail(email: string): Observable<any> {
    return this.http.get<any>(`${this.url}/api/assigned-tests/assigned-tests/${email}`);
  }

}

