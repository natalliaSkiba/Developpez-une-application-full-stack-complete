import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Comment } from '../models/comment.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CommentService {
  private baseUrl =  `${environment.apiUrl}/articles`;

  constructor(private http: HttpClient) {}

  getCommentsByArticleId(articleId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.baseUrl}/${articleId}/comments`);
  }

  addComment(articleId: number,  content: string): Observable<Comment> {
    
    return this.http.post<Comment>(`${this.baseUrl}/${articleId}/comments`, null, { params:{comment:content} });
  }
}
