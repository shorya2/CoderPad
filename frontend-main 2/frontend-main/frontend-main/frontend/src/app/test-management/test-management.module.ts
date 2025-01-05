import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TestManagementRoutingModule } from './test-management-routing.module';
import { CreateTestComponent } from './components/create-test/create-test.component';
import { ReactiveFormsModule } from '@angular/forms';
import { UpdateTestComponent } from './components/update-test/update-test.component';
import { TestListComponent } from './components/test-list/test-list.component';
import { CreateQuestionComponent } from './components/create-question/create-question.component';
import { UpdateQuestionComponent } from './components/update-question/update-question.component';
import { QuestionListComponent } from './components/question-list/question-list.component';
import { TestQuestionsComponent } from './components/test-questions/test-questions.component';
import { AttemptedTestComponent } from './components/attempted-test/attempted-test.component';
import { FinishedTestComponent } from './components/finished-test/finished-test.component';


@NgModule({
  declarations: [
    CreateTestComponent,
    UpdateTestComponent,
    TestListComponent,
    CreateQuestionComponent,
    UpdateQuestionComponent,
    QuestionListComponent,
    TestQuestionsComponent,
    AttemptedTestComponent,
    FinishedTestComponent
  ],
  imports: [
    CommonModule,
    TestManagementRoutingModule,
    ReactiveFormsModule
  ]
})
export class TestManagementModule { }
