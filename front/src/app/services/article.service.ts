import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Article } from '../models/article.model';



@Injectable({
  providedIn: 'root'
})
export class ArticleService {
  private apiUrl = 'http://localhost:8080/articles';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Article[]> {
    return this.http.get<Article[]>(this.apiUrl);
  }

  getFeedForUser(userId: number): Observable<Article[]> {
    return this.http.get<Article[]>(`${this.apiUrl}/feed/${userId}`);
  }

  getArticleById(id: number): Observable<Article> {
    return this.http.get<Article>(`${this.apiUrl}/${id}`);
  }
  
  createArticle(articleData: {
    title: string;
    content: string;
    topicId: number;
    authorId: number;
  }): Observable<any> {
    return this.http.post(`${this.apiUrl}`, articleData, {
      responseType: 'text'
    });
  }
}
