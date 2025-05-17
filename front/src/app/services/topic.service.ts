import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Topic, TopicResponse } from '../models/topic.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class TopicService {
  
  private apiUrl =  `${environment.apiUrl}/topics`;

  constructor(private http: HttpClient) {}

  getAllTopics(): Observable<Topic[]> {
    return this.http.get<Topic[]>(this.apiUrl);
  }

  subscribeToTopic( topicId: number): Observable<string> {
    return this.http.post(`${this.apiUrl}/subscribe/${topicId}`, null, {
      responseType: 'text',
    });
  }
  
  unsubscribeFromTopic( topicId: number): Observable<string> {
    return this.http.post(`${this.apiUrl}/unsubscribe/${topicId}`, null, {
      responseType: 'text',
    });
  }

  getAllTopicsWithStatus(): Observable<TopicResponse[]>{
    return this.http.get<TopicResponse[]>(`${this.apiUrl}/subscriptions`);
  }
  
  
}
