import { Component, OnInit } from '@angular/core';
import { TopicService } from 'src/app/services/topic.service';
import { TopicResponse } from 'src/app/models/topic.model';

@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss'],
})
export class TopicsComponent implements OnInit {
  topics: TopicResponse [] = [];
  userId = 2; // temp

  constructor(private topicService: TopicService) {}

  ngOnInit(): void {
    this.topicService.getAllTopicsForUser(this.userId).subscribe((data) => {
      this.topics = data;

    });
  }

  isSubscribed(topic: TopicResponse): boolean {
    return topic.subscribed;
  }

  subscribe(topic: TopicResponse): void {
    this.topicService.subscribeToTopic(this.userId, topic.id).subscribe(() => {
      topic.subscribed = true;
    });
  }

  unsubscribe(topic: TopicResponse): void {
    this.topicService.unsubscribeFromTopic(this.userId, topic.id).subscribe(() => {
      topic.subscribed = false;
    });
  }
}
