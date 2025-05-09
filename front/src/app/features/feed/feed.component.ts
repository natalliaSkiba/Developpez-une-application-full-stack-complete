import { Component, OnInit } from '@angular/core';
import { ArticleService } from 'src/app/services/article.service';
import { Article } from 'src/app/models/article.model';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.scss']
})
export class FeedComponent implements OnInit {
  articles: Article[] = [];
  sortOption: 'asc' | 'desc' = 'desc';
  

  constructor(private articleService: ArticleService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    const topicId = this.route.snapshot.paramMap.get('topicId');

    if (topicId) {
      this.articleService.getArticlesByTopic(+topicId).subscribe({
        next: (data) => {
          this.articles = data;
          this.sortArticles();
        },
        error: (err) => console.error('Erreur lors du chargement des articles par thématique', err)
      });
    } else {
      this.articleService.getUserFeed().subscribe({
        next: (data) => {
          this.articles = data;
          this.sortArticles();
        },
        error: (err) => console.error('Erreur lors du chargement du feed', err)
      });
    }
  }

  sortArticles(): void {
    this.articles.sort((a, b) => {
      const dateA = new Date(a.createdAt).getTime();
      const dateB = new Date(b.createdAt).getTime();
      return this.sortOption === 'desc' ? dateB - dateA : dateA - dateB;
    });
  }

  onSortChange(): void {
    this.sortArticles();
  }
}
