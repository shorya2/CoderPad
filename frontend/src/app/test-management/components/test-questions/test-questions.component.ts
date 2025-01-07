import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TestService } from 'src/app/services/test/test.service';

@Component({
  selector: 'app-test-questions',
  templateUrl: './test-questions.component.html',
  styleUrls: ['./test-questions.component.scss']
})
export class TestQuestionsComponent {
  testId!: string;
  testDetails: any = null;
  questions: any[] = [];

  constructor(
    private route: ActivatedRoute,
    private testService: TestService,
    private router : Router
  ) {}

  ngOnInit(): void {
    this.testId = this.route.snapshot.params['testId'];
    // this.testId = "ae33"
    this.loadTestQuestions();
  }

  loadTestQuestions(): void {
    this.testService.getTestById(this.testId).subscribe({
      next: (test) => {
        this.testDetails = test;
        this.questions = test.questions || [];
      },
      error: (err) => {
        console.error('Error fetching test:', err);
      },
    });
  }

  navigateToQuestionList(): void {
    this.router.navigate(['/question-list', this.testId]);
  }

  navigateToUpdateQuestion(id: string): void {
    this.router.navigate(['/update-question', id]);
  }


  deleteQuestionFromTest(questionId: string): void {
    this.testService.getTestById(this.testId).subscribe((test) => {
      if (test.questions) {
        // Remove question from test's question array
        test.questions = test.questions.filter((q: any) => q.id !== questionId);

        // Save updated test
        this.testService.updateTest(this.testId, test).subscribe(() => {
          console.log('Question removed successfully');

          this.loadTestQuestions(); // Refresh the questions list
        });
      }
    });
  }
}
