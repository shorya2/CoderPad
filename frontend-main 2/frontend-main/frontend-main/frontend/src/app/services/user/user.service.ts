import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Observable } from 'rxjs';
import { AuthService } from '../auth.service';
@Injectable({
  providedIn: 'root'
})
export class UserService {
  private baseUrl = 'http://localhost:3000/users'; // Adjust this URL as needed
  private apiUrl = 'http://localhost:8090/users'; // Updated backend URL

  constructor(private http: HttpClient, private authService: AuthService) {}

  getUsersByIds(ids: string[]): Observable<any[]> {
    // Adjust API endpoint as necessary
    return this.http.post<any[]>(`${this.baseUrl}/users/getUsersByIds`, { ids });
  }
  
  // getUserById(id: number): Observable<any>{
  //     return this.http.get<any>(`${this.apiUrl}/users/getUserById?id=${id}`);
  // }
  getUserById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/getUserById?id=${id}`);
}

  // Method to call the backend API for signup
  userSignUp(userData: any): Observable<any> {
    //return this.http.post<any>(this.apiUrl, userData);
    return this.http.post<any>(`${this.apiUrl}/register`, userData);
  }

  getProfile(id:number|null): Observable<any[]> {
    //const id = localStorage.getItem('userId');
    console.log(id); 
    const token = this.authService.getToken(); // Get the JWT token
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    
    return this.http.get<any[]>(`${this.apiUrl}/profile?userId=${id}`, { headers });
  }

  updateProfile(id:number|null,profileData: any): Observable<any> {
    console.log(id);
    const token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    const url = `${this.apiUrl}/update/${id}`
    return this.http.put(url, profileData,{headers});
  }

  changePassword(id:number|null,passwordData:any): Observable<any> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    const url = `${this.apiUrl}/updatePassword/${id}`
    return this.http.put(url, passwordData,{headers});
  }

  getAllUsers(): Observable<any[]> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get<any[]>(`http://localhost:8090/users/admin/getAllUsers`,{headers});
  }

  getAllCandidates(): Observable<any[]>{
    const token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    console.log(token);
    return this.http.get<any[]>(`http://localhost:8090/users/getAllCandidates`,{headers});
  }

  getTestById(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }
  // Delete a User
  deleteUser(id: number): Observable<any> {
    const token = this.authService.getToken();
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);// Include JWT token
  return this.http.delete(`${this.apiUrl}/admin/deleteUser?id=${id}`, { headers });
  }
  // // Update User Detail
  // updateUser(id:string, userData: any): Observable<any> {
  //   return this.http.patch(`${this.apiUrl}/${id}`,userData);
  // }


  getPendingApprovals(): Observable<any[]> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`); // Include JWT token

    
    return this.http.get<any[]>(`${this.apiUrl}/admin/getAllNotApproved`,{headers});
  }

  approveUserRole(userEmail: string): Observable<any> {
    const headers = new HttpHeaders().set('Authorization', `Bearer ${this.authService.getToken()}`); // Include JWT token

    return this.http.put<any>(`${this.apiUrl}/admin/approveUser?userEmail=${userEmail}`, {},{headers});
  }
}
