import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { QuestionService } from 'src/app/services/question/question.service';

@Component({
  selector: 'app-create-question',
  templateUrl: './create-question.component.html',
  styleUrls: ['./create-question.component.scss']
})
export class CreateQuestionComponent {
  questionForm: FormGroup;

  constructor(private fb: FormBuilder, private questionService: QuestionService) {
    this.questionForm = this.fb.group({
      questionDescription: ['', Validators.required],
      quesType: ['mcq', Validators.required],
      difficulty: ['', Validators.required],
      option1: ['null', Validators.required],
      option2: ['null', Validators.required],
      option3: ['null', Validators.required],
      option4: ['null', Validators.required],
      correctAnswer: ['', Validators.required],
    });
  }

  saveQuestion() {
    const questionData = this.questionForm.value;

    // Save question
    this.questionService.createQuestion(questionData).subscribe({
      next: (response) => {
        console.log('Question saved:', response);
        this.questionForm.reset(); // Reset the form
      },
      error: (error) => {
        console.error('Error saving question:', error);
      }
    });

  }
}
