import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { QuestionService } from 'src/app/services/question/question.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-update-question',
  templateUrl: './update-question.component.html',
  styleUrls: ['./update-question.component.scss']
})
export class UpdateQuestionComponent {
  questionForm!: FormGroup;
  questionId!: string;

  constructor(
    private fb: FormBuilder,
    private questionService: QuestionService,
    private route: ActivatedRoute,
    private router: Router,
    private location : Location
  ) {}

  ngOnInit(): void {
    // Initialize the form
    this.questionForm = this.fb.group({
      questionDescription: [null, Validators.required],
      quesType: ['null', Validators.required],
      difficulty: ['', Validators.required],
      option1: ['null',Validators.required],
      option2: ['null',Validators.required],
      option3: ['null',Validators.required],
      option4: ['null',Validators.required],
      ddlCommands:['null',Validators.required],
      dmlCommands:['null',Validators.required],
      correctAnswer: ['', Validators.required]
    });

    // Get the question ID from the route
    this.questionId = this.route.snapshot.paramMap.get('id')!;
    // this.questionId = "7a4d";
    this.loadQuestionDetails();
  }

  loadQuestionDetails(): void {
    // Fetch the question details by ID
    this.questionService.getQuestionById(this.questionId).subscribe({
      next: (question) => {
        this.questionForm.patchValue(question); // Populate the form with the fetched question data
      },
      error: (error) => {
        console.error('Error fetching question:', error);
      },
      complete: () => {
        console.log('Question fetching completed');
      },
    });

  }

  updateQuestion(): void {
    if (this.questionForm.valid) {
      this.questionService.updateQuestion(this.questionId, this.questionForm.value).subscribe({
        next: () => {
          console.log('Question updated successfully');
          // this.router.navigate(['/questions']);
        },
        error: (error) => {
          console.error('Error updating question:', error);
        }
      });

    }
  }
  goBack(){
    this.location.back();
  }
}
