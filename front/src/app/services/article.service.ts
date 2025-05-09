import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Article } from '../models/article.model';
import { User } from '../models/user.model';
import { environment } from 'src/environments/environment';



@Injectable({
  providedIn: 'root'
})
export class ArticleService {
    private apiUrl =  `${environment.apiUrl}/articles`;


  constructor(private http: HttpClient) {}

  getAll(): Observable<Article[]> {
    return this.http.get<Article[]>(this.apiUrl);
  }
  
  getUserFeed(): Observable<Article[]> {
    return this.http.get<Article[]>(`${this.apiUrl}/feed`);
  }

  getArticleById(id: number): Observable<Article> {
    return this.http.get<Article>(`${this.apiUrl}/${id}`);
  }
  getArticlesByTopic(topicId: number): Observable<Article[]> {
    return this.http.get<Article[]>(`${this.apiUrl}/by-topic/${topicId}`);
  }
  
  createArticle(articleData: {
    title: string;
    content: string;
    topicId: number;
   
  }): Observable<any> {
    return this.http.post(`${this.apiUrl}`, articleData, {
      responseType: 'text'
    });
  }
}
