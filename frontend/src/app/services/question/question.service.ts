import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, switchMap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class QuestionService {
  private quesUrl = 'http://localhost:3000/questions';
  private testUrl = 'http://localhost:3000/tests';
  constructor(private http: HttpClient) {}

  createQuestion(questionData: any): Observable<any> {
    return this.http.post(this.quesUrl, questionData);
  }

  getAllQuestions(): Observable<any[]> {
    return this.http.get<any[]>(this.quesUrl);
  }

  getQuestionById(id: string): Observable<any> {
    return this.http.get(`${this.quesUrl}/${id}`);
  }

  updateQuestion(id: string, questionData: any): Observable<any> {
    return this.http.patch(`${this.quesUrl}/${id}`, questionData);
  }

  addQuestionToTest(testId: string, question: any): Observable<any> {

    return this.http.get<any>(`${this.testUrl}/${testId}`).pipe(
      switchMap((test) => {

        // Ensure the `questions` array exists
        if (!test.questions) {
          test.questions = [];
        }

        // Add the question to the `questions` array
        test.questions.push(question);

        // Update the test object
        return this.http.put(`${this.testUrl}/${testId}`, test);
      })
    );
  }
}
