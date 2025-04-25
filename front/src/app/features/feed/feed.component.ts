import { Component, OnInit } from '@angular/core';
import { ArticleService } from 'src/app/services/article.service';
import { Article } from 'src/app/models/article.model';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.scss']
})
export class FeedComponent implements OnInit {
  articles: Article[] = [];
  sortOption: 'asc' | 'desc' = 'desc';
  userId = 2; //temp

  constructor(private articleService: ArticleService) {}

  ngOnInit(): void {
    this.articleService.getFeedForUser(this.userId).subscribe({
      next: (data) => {
        this.articles = data;
        this.sortArticles();
      },
      error: (err) => console.error('Erreur lors du chargement des articles', err)
    });
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
