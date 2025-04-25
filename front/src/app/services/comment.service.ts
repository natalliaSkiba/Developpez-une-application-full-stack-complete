import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Comment } from '../models/comment.model';
@Injectable({
  providedIn: 'root',
})
export class CommentService {
  private baseUrl = 'http://localhost:8080/articles';

  constructor(private http: HttpClient) {}

  getCommentsByArticleId(articleId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.baseUrl}/${articleId}/comments`);
  }

  addComment(articleId: number, userId: number, content: string): Observable<Comment> {
    const params = {
      userId: userId.toString(),
      comment: content
    };
    return this.http.post<Comment>(`${this.baseUrl}/${articleId}/comments`, null, { params });
  }
}
