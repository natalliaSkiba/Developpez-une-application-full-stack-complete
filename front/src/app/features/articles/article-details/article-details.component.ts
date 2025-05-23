import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
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
export class ArticleDetailsComponent implements OnInit, OnDestroy {
  articleId!: number;
  article!: Article;
  comments: Comment[] = [];
  newComment: string = '';
  private destroy$ = new Subject<void>();

  constructor(
    private route: ActivatedRoute,
    private articleService: ArticleService,
    private commentService: CommentService
  ) {}

  ngOnInit(): void {
    this.articleId = Number(this.route.snapshot.paramMap.get('id'));

    this.articleService
      .getArticleById(this.articleId)
      .pipe(takeUntil(this.destroy$))
      .subscribe((data) => {
        this.article = data;
      });

    this.loadComments();
  }

   ngOnDestroy(): void {
    this.destroy$.next();      
    this.destroy$.complete(); 
  }

  loadComments(): void {
    this.commentService
      .getCommentsByArticleId(this.articleId)
      .pipe(takeUntil(this.destroy$))
      .subscribe((data) => {
        this.comments = data;
      });
  }

  submitComment(): void {
    if (!this.newComment.trim()) return;

    this.commentService
      .addComment(this.articleId, this.newComment.trim())
      .pipe(takeUntil(this.destroy$))
      .subscribe(() => {
        this.newComment = '';
        this.loadComments();
      });
  }
}
