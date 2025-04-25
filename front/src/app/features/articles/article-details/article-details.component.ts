import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Article } from 'src/app/models/article.model';
import { Comment } from 'src/app/models/comment.model';
import { ArticleService } from 'src/app/services/article.service';
import { CommentService } from 'src/app/services/comment.service';

@Component({
  selector: 'app-article-details',
  templateUrl: './article-details.component.html',
  styleUrls: ['./article-details.component.scss'],
})
export class ArticleDetailsComponent implements OnInit {
  articleId!: number;
  article!: Article;
  comments: Comment[] = [];
  newComment: string = '';
  userId: number =2; //temp

  constructor(
    private route: ActivatedRoute,
    private articleService: ArticleService,
    private commentService: CommentService
  ) {}

  
  ngOnInit(): void {
    this.articleId = Number(this.route.snapshot.paramMap.get('id'));

    this.articleService.getArticleById(this.articleId).subscribe((data) => {
      this.article = data;
    });

    this.loadComments();
  }

  loadComments(): void {
    this.commentService.getCommentsByArticleId(this.articleId).subscribe((data) => {
      this.comments = data;
    });
  }

  submitComment(): void {
    if (!this.newComment.trim()) return;

    this.commentService.addComment(this.articleId, this.userId, this.newComment.trim()).subscribe(() => {
      this.newComment = '';
      this.loadComments(); // обновляем список комментариев после отправки
    });
  }
}
