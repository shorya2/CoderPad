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
    this.userService.getPendingApprovals().subscribe({
      next: (data) => {
        this.users = data.filter((user: any) => user.userrole !== 'candidate');
        this.isLoading = false;
      },
      error: (err) => {
        this.toastr.error('Failed to load users');
        this.isLoading = false;
      },
    });
  }

  approveUser(userId: string): void {
    this.userService.approveUserRole(userId).subscribe({
      next: () => {
        this.toastr.success('User approved successfully');
        this.users = this.users.filter(user => user.id !== userId);
      },
      error: () => {
        this.toastr.error('Failed to approve user');
      },
    });
  }
}
