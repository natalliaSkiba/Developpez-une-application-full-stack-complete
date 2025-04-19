import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Topic, TopicResponse } from '../models/topic.model';


@Injectable({
  providedIn: 'root',
})
export class TopicService {
  private apiUrl = 'http://localhost:8080/topics';

  constructor(private http: HttpClient) {}

  getAllTopics(): Observable<Topic[]> {
    return this.http.get<Topic[]>(this.apiUrl);
  }

  subscribeToTopic(userId: number, topicId: number): Observable<string> {
    return this.http.post(`${this.apiUrl}/subscribe/${userId}/${topicId}`, null, {
      responseType: 'text',
    });
  }
  
  unsubscribeFromTopic(userId: number, topicId: number): Observable<string> {
    return this.http.post(`${this.apiUrl}/unsubscribe/${userId}/${topicId}`, null, {
      responseType: 'text',
    });
  }

  getAllTopicsForUser(userId: number): Observable<TopicResponse[]> {
    return this.http.get<TopicResponse[]>(`${this.apiUrl}/${userId}`);
  }
  
}
