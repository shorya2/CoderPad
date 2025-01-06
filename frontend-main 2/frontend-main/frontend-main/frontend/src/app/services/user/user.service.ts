import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class UserService {
  //private apiUrl = 'http://localhost:3000/users'; // Adjust this URL as needed
  private apiUrl = 'http://localhost:8080/users'; // Updated backend URL

  constructor(private http: HttpClient) {}

  // Method to call the backend API for signup
  userSignUp(userData: any): Observable<any> {
    //return this.http.post<any>(this.apiUrl, userData);
    return this.http.post<any>(`${this.apiUrl}/register`, userData);
  }

  getProfile(): Observable<any> {
    return this.http.get(`${this.apiUrl}`);
  }

  updateProfile(id:string,profileData: any): Observable<any> {
    // const url = `http://localhost:3000/users/${id}`
    // return this.http.patch(url, profileData);
    return this.http.put(`${this.apiUrl}/update/${id}`, profileData);
  }

  changePassword(id:string,passwordData:any): Observable<any> {
    // const url = `http://localhost:3000/users/${id}`
    // return this.http.patch(url, passwordData);
    return this.http.put(`${this.apiUrl}/updatePassword/${id}`, passwordData);
  }

  getAllUsers(): Observable<any[]> {
    // return this.http.get<any[]>(this.apiUrl);
    return this.http.get<any[]>(`${this.apiUrl}/admin/getAllUsers`);
  }

  // Delete a User
  deleteUser(id: string): Observable<any> {
    // return this.http.delete(`${this.apiUrl}/${id}`);
    return this.http.delete(`${this.apiUrl}/${id}`);
  }

  // // Update User Detail
  // updateUser(id:string, userData: any): Observable<any> {
  //   return this.http.patch(`${this.apiUrl}/${id}`,userData);
  // }

  getPendingApprovals(): Observable<any[]> {
    // return this.http.get<any[]>(`${this.apiUrl}?isActive=false`);
    return this.http.get<any[]>(`${this.apiUrl}/admin/getAllNotApproved`);
  }

  approveUserRole(userId: string): Observable<void> {
    // return this.http.patch<void>(`${this.apiUrl}/${userId}`, { isActive : true });
    return this.http.put<void>(`${this.apiUrl}/admin/approveUser`, { userEmail: userId });
  }
}
