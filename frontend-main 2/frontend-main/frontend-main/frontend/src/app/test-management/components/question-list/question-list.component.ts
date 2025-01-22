import { Component } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { QuestionService } from 'src/app/services/question/question.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-question-list',
  templateUrl: './question-list.component.html',
  styleUrls: ['./question-list.component.scss']
})
export class QuestionListComponent {
  questions: any[] = [];
  selectedTestId = this.route.snapshot.params['testId'];
  constructor(private questionService: QuestionService, private router: Router,private route:ActivatedRoute,private location :Location) {}

  // isQuestionInTest(questionId: string): boolean {
  //   return this.questions.some((q: any) => q.id === questionId);
  // }

  ngOnInit(): void {
    this.fetchQuestions();
  }

  fetchQuestions(): void {
    this.questionService.getAllQuestions().subscribe({
      next: (data: any[]) => {
        this.questions = data;
      },
      error: (err: any) => {
        console.error('Error fetching questions:', err);
      },
    });
  }
    goBack(){
      this.location.back();
  }
  addToTest(question: any): void {
    const questionId = question.id;
    this.questionService.addQuestionToTest(this.selectedTestId, questionId).subscribe({
      next: () => {
        alert(`Question "${question.id}" added to test.`);
      },
      error: (error: any) => {
        console.error('Error adding question to test:', error);
      },
    });
  }
}
