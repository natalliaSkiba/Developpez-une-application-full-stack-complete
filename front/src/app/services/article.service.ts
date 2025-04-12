import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Article {
  id: number;
  title: string;
  content: string;
  createdAt: string;
  author: {
    id: number;
    username: string;
  };
  topic: {
    id: number;
    name: string;
  };
}

@Injectable({
  providedIn: 'root'
})
export class ArticleService {
  private apiUrl = 'http://localhost:8080/articles';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Article[]> {
    return this.http.get<Article[]>(this.apiUrl);
  }
  createArticle(articleData: {
    title: string;
    content: string;
    topicId: number;
    authorId: number;
  }): Observable<any> {
    return this.http.post('http://localhost:8080/articles', articleData, {
      responseType: 'text'
    });
  }
}
