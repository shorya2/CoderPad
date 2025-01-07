import { Component , OnInit} from '@angular/core';
import { UserService } from 'src/app/services/user/user.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent {
  profileForm: FormGroup;
  passwordForm: FormGroup;
  id : number|null = null;
  userName : string ='';
  email : string = '';
  password : string = '';
  constructor(private userService:UserService, private fb:FormBuilder,private toastr:ToastrService,private route:ActivatedRoute, private authService: AuthService){
    this.profileForm = this.fb.group({
      userName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
    });

    this.passwordForm = this.fb.group({
      // currentPassword: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      cnfpassword: ['', Validators.required],
    });
  }
  ngOnInit(): void {
    this.id = parseInt(localStorage.getItem('userId') || 'null');
    this.loadUserProfile();
  }

  loadUserProfile(): void {
    console.log("hi");
    console.log(this.id);
   
      this.userService.getProfile(this.id).subscribe((value: any) => {
        this.profileForm.patchValue({
          userName: value.userName,
          email: value.email
        });
      });
  
      this.userService.getProfile(this.id).subscribe((value: any) => {
        this.passwordForm.patchValue({
          password: value.password
        });
      });
    
  }
 

  onUpdateProfile(): void {
    if (this.profileForm.valid) {
        // Construct the profile data
        const profileData = {
            userName: this.profileForm.value.userName,
            email: this.profileForm.value.email
        };
        console.log(this.id + profileData.email);

        if (this.id !== null && this.id !== undefined) {
            this.userService.updateProfile(this.id, profileData).subscribe({
                next: (response) => {
                    this.toastr.success('Profile updated successfully');
                    console.log(response);  // Log the response to check the result
                },
                error: (error) => {
                  console.error('Error occurred while updating profile:', error);
                  // Handle different HTTP status codes
              },
            });
        } else {
            this.toastr.error('User ID is missing!');
        }
    } else {
        this.toastr.error('Form is invalid');
    }
}

onChangePassword(): void {
    if (this.passwordForm.valid) {
        const { password, cnfpassword } = this.passwordForm.value;

        if (password !== cnfpassword) {
            this.toastr.error('Passwords do not match');
            return;
        }

        const passwordData = {
            password: this.passwordForm.value.password
        };

        this.userService.changePassword(this.id, passwordData).subscribe({
            next: (response) => {
                this.toastr.success('Password changed successfully');
                console.log(response);
            },
            error: (error) => {
                this.toastr.error('Failed to change password',error);
            },
        });
    }
}
}

