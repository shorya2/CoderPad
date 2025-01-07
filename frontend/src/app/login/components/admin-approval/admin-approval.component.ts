import { Component } from '@angular/core';
import { UserService } from 'src/app/services/user/user.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-admin-approval',
  templateUrl: './admin-approval.component.html',
  styleUrls: ['./admin-approval.component.scss']
})
export class AdminApprovalComponent {
  users: any[] = [];
  isLoading: boolean = true;

  constructor(private userService: UserService, private toastr: ToastrService) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.isLoading = true; // Set loading to true while fetching data
    this.userService.getPendingApprovals().subscribe({
      next: (data) => {
        this.users = data; // Filter out non-approved users
        this.isLoading = false; // Set loading to false when data is fetched
      },
      error: (err) => {
        this.toastr.error('Failed to load users');  // Show error if request fails
        this.isLoading = false; // Set loading to false in case of error
      },
    });
  }

  approveUser(userEmail: string): void {
    this.userService.approveUserRole(userEmail).subscribe({
      next: () => {
        this.toastr.success('User approved successfully');
        this.users = this.users.filter(user => user.useremail !== userEmail); // Filter out approved user
      },
      error: () => {
        this.toastr.error('Failed to approve user');
      },
    });
  }
}
