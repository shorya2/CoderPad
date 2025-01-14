import { Component } from '@angular/core';
import { UserService } from 'src/app/services/user/user.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent {
  users: any[] = [];
  isLoading = false;
  searchTerm: string =''

  constructor(private userService: UserService, private toastr: ToastrService) {}

  ngOnInit(): void {
    this.getUsers();
  }

 // Fetch Users
 getUsers(): void {
  console.log("hi");
  this.isLoading = true;
  this.userService.getAllUsers().subscribe({
    next: (data) => {
      this.users = data;
      this.isLoading = false;
    },
    error: (error) => {
      console.log(error);
      this.toastr.error('Failed to load users.', 'Error');
      this.isLoading = false;
    }
  });
}

searchUser(): void {
  if (!this.searchTerm.trim()) {
    this.toastr.warning('Please enter a username to search.', 'Warning');
    return;
  }

  this.isLoading = true;
  this.userService.getAllUsers().subscribe({
    next: (data) => {
      const matchedUser = data.find(
        (user: any) =>
          user.userName.toLowerCase() === this.searchTerm.toLowerCase()
      );

      if (matchedUser) {
        this.users = [matchedUser]; // Show only the matched user
      } else {
        this.users = []; // Clear the list if no user matches
        this.toastr.info('No user found with the given username.', 'Info');
      }

      this.isLoading = false;
    },
    error: (error) => {
      console.error(error);
      this.toastr.error('Failed to search user.', 'Error');
      this.isLoading = false;
    },
  });
}
//Delete
  deleteUser(id: number): void {
    if (confirm('Are you sure you want to delete this user?')) {
      this.userService.deleteUser(id).subscribe({
        next: () => {
          this.toastr.success('User deleted successfully!', 'Success');
          this.getUsers(); // Refresh the user list
        },
        error: (err) => {
          this.toastr.error('Failed to delete user.', 'Error');
        }
      });
    }
  }


  // Edit user (you can implement an edit form modal or redirect to an edit page)
  editUser(id:number): void {
    console.log('Edit user with ID:', id);
    
  }
}
