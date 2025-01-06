import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoadDataService {

  // Path to your JSON file containing both tests and assignments
  private dataUrl = 'assets/data.json';  // Path to your data.json file

  // Backend URL to fetch user emails
  private candidateEmailsUrl = 'http://localhost:8080/api/candidate-emails';  // Backend API for emails

  constructor(private http: HttpClient) { }

  // Fetching tests and assignments from data.json
  getData(): Observable<any> {
    return this.http.get<any>(this.dataUrl);
  }

  // Method to fetch candidate emails from the backend
  getCandidateEmails(): Observable<string[]> {
    return this.http.get<string[]>(this.candidateEmailsUrl);
  }

}
