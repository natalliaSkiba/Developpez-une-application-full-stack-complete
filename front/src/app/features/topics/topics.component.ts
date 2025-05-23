import { Component, OnInit, OnDestroy } from '@angular/core';
import { TopicService } from 'src/app/services/topic.service';
import { TopicResponse } from 'src/app/models/topic.model';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss'],
})
export class TopicsComponent implements OnInit, OnDestroy {
  topics: TopicResponse[] = [];
  private destroy$ = new Subject<void>();

  constructor(private topicService: TopicService, private router: Router) {}

  ngOnInit(): void {
    this.topicService
      .getAllTopicsWithStatus()
      .pipe(takeUntil(this.destroy$))
      .subscribe((data) => {
        this.topics = data;
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  goToTopicArticles(topicId: number): void {
    this.router.navigate(['/feed/topic', topicId, 'articles']);
  }

  isSubscribed(topic: TopicResponse): boolean {
    return topic.subscribed;
  }

  subscribe(topic: TopicResponse): void {
    this.topicService 
    .subscribeToTopic(topic.id).subscribe(() => {
      topic.subscribed = true;
    });
  }

  unsubscribe(topic: TopicResponse): void {
    this.topicService.unsubscribeFromTopic(topic.id).subscribe(() => {
      topic.subscribed = false;
    });
  }
}
