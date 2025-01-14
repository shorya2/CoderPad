import { Component } from '@angular/core';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.scss']
})
export class TestComponent {
userName: string | null = null;
  id : number|null = null;

  constructor(private userService: UserService){}

  ngOnInit(): void {
    this.id = parseInt(localStorage.getItem('userId') || 'null');
    this.getUserName();
  }


  getUserName(): void {
    console.log(this.id);
    if (this.id !== null) {
      this.userService.getProfile(this.id).subscribe((value:any)=>{
        this.userName = value.userName;
      });
    } else {
      console.warn('User ID is null, unable to fetch profile');
    }
  }
}
