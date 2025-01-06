import { Component , OnInit} from '@angular/core';
import { UserService } from 'src/app/services/user/user.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent {
  profileForm: FormGroup;
  passwordForm: FormGroup;
  constructor(private userService:UserService, private fb:FormBuilder,private toastr:ToastrService){
    this.profileForm = this.fb.group({
      username: ['', Validators.required],
      useremail: ['', [Validators.required, Validators.email]],
    });

    this.passwordForm = this.fb.group({
      // currentPassword: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      cnfpassword: ['', Validators.required],
    });
  }
  ngOnInit(): void {
    this.loadUserProfile();
  }

  loadUserProfile(): void {
    this.userService.getProfile().subscribe((value:any) => {
      this.profileForm.patchValue({
        username: value[0].username,
        useremail: value[0].useremail
      });
      // console.log(value[0].username);
    });
    this.userService.getProfile().subscribe((value:any) => {
      this.passwordForm.patchValue({
        password:value[0].password
      })
    });
  }

  onUpdateProfile(): void {
    if (this.profileForm.valid) {
      const id = "9440";
      this.userService
        .updateProfile(id,this.profileForm.value)
        .subscribe({
          next: () => {
            this.toastr.success('Profile updated successfully');
          },
          error: (error) => {
            this.toastr.error('Failed to update profile');
          },
        });
    }
  }

  onChangePassword(): void {
    if (this.passwordForm.valid) {
      const { password, cnfpassword } =
        this.passwordForm.value;

      if (password !== cnfpassword) {
        this.toastr.error('Passwords do not match');
        return;
      }
      if(this.passwordForm.valid){
         const id = "9440";
         this.userService.changePassword(id,this.passwordForm.value).subscribe({
          next: () => {
            this.toastr.success('Password changed successfully');
          },
          error: (error) => {
            this.toastr.error('Failed to change password');
          },
        });
      }
    }
  }
}
