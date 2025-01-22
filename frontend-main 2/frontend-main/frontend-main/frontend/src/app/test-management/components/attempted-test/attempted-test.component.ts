import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TestService } from 'src/app/services/test/test.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-attempted-test',
  templateUrl: './attempted-test.component.html',
  styleUrls: ['./attempted-test.component.scss']
})
export class AttemptedTestComponent {
  userId!:number;
  userDetails: any = null;
  candidateId: string = ''
  testId: string = "e274";
  testDetails: any = null;
  testSubmitted !: boolean;
  questions: any[] = [];
  attemptedAnswers: { [key: string]: string[]} = {};
  timer!: number; // Timer in seconds
  intervalId: any;
  assignedTests: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private testService: TestService,
    private userService: UserService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.userId = parseInt(localStorage.getItem('userId') || 'null');
    this.candidateId = this.userId.toString();
    this.testId = this.route.snapshot.params['id'];
    this.testSubmitted = false;
    this.loadTestDetails();
    this.loadUserDetails();
    this.loadAssignedTests();
  }

  // Fetch assigned tests from server
  loadAssignedTests(): void {
    this.testService.getAssignedTests().subscribe((tests) => {
      this.assignedTests = tests;
    });
  }

  loadTestDetails(): void {
    this.testService.getTestById(this.testId).subscribe({
      next: (test) => {
        this.testDetails = test;
        this.questions = test.questions;
        // console.log(this.questions);
        this.timer = test.duration * 60; // Converting to Seconds
        this.startTimer();
      },
      error: (error) => console.error('Error fetching test:', error),
    });
  }
 // Load the User Details
  loadUserDetails() : void{
    this.userService.getUserById(this.userId).subscribe({
        next: (candidate) => {
          this.userDetails = candidate;
          console.log(candidate);
        },
        error: (error) => console.error('Error Fetching Candidate Details:',error),
    })
  }
  getOptions(question: any): string[] {
    return [question.option1, question.option2, question.option3, question.option4];
  }

  getTextAreaValue(event: Event): string {
    const target = event.target as HTMLTextAreaElement;
    return target ? target.value : '';
  }

  selectAnswer(questionId: string, value: number): void {
    if (value) {
      this.attemptedAnswers[questionId] = [value.toString()]; // Save the selected answer
    }
  }

  selectAnswerSQL(questionId: string, value: string): void {
    if (value) {
      this.attemptedAnswers[questionId] = [value]; // Save the selected answer as an array
    }
  }

  isChecked(event: Event): boolean {
    const target = event.target as HTMLInputElement;
    return target ? target.checked : false;
  }

  toggleMultiSelectAnswer(questionId: string, option: number, checked: boolean): void {
    if (!this.attemptedAnswers[questionId]) {
      this.attemptedAnswers[questionId] = [];
    }

    if (checked) {
      // Add checked options
      this.attemptedAnswers[questionId].push(option.toString());
    } else {
      // Remove unchecked options
      const index = this.attemptedAnswers[questionId].indexOf(option.toString());
      if (index > -1) {
        this.attemptedAnswers[questionId].splice(index, 1);
      }
    }
  }

  formatTime(seconds: number): string {
    const minutes = Math.floor(seconds / 60);
    const remainingSeconds = seconds % 60;
    return `${minutes.toString().padStart(2, '0')}:${remainingSeconds
      .toString()
      .padStart(2, '0')}`;
  }

  startTimer(): void {
    this.intervalId = setInterval(() => {
      if (this.timer > 0) {
        this.timer--;
      } else {
        clearInterval(this.intervalId);
        this.submitTest();
      }
    }, 1000);
  }

  submitTest(): void {

    if (this.testSubmitted) return;
    this.testSubmitted = true;

    const attemptedTest = {
      testId: this.testId,
      questions: this.questions.map((question) => {
        const selectedAnswers = this.attemptedAnswers[question.id];
        console.log(selectedAnswers);
        return {
          questionDescription: question.questionDescription,
          selectedAnswer: selectedAnswers.join(','),
          correctAnswer: question.correctAnswer, // Correct answer as string
        };// Correct answer
      }),
    };

    this.testService.submitAttemptedTest(attemptedTest).subscribe({
      next: () => {
        console.log('Test submitted successfully');
        alert("Test Has Been Submitted!!");
        // console.log();
        this.router.navigate(['/assignedc']); // Navigate back
      },
      error: (error) => console.error('Error submitting test:', error),
    });
    
     // Remove the test from the assigned tests list
     this.testService.removeAssignedTest(this.userDetails.email,this.testDetails.createdBy).subscribe(() => {
      console.log(this.userDetails.email);
      console.log(this.testDetails.createdBy);
      this.loadAssignedTests(); // Refresh the assigned tests list
    });

  }

  ngOnDestroy(): void {
    if (this.intervalId) {
      clearInterval(this.intervalId); // Clear the timer when component is destroyed
    }
  }
}