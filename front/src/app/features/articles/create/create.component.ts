import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { ArticleService } from 'src/app/services/article.service';
import { Route, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Topic } from 'src/app/models/topic.model';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-create-article',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.scss'],
})
export class CreateComponent implements OnInit, OnDestroy {
  articleForm!: FormGroup;
  topics: Topic[] = [];
  private destroy$ = new Subject<void>();

  constructor(
    private fb: FormBuilder,
    private location: Location,
    private articleService: ArticleService,
    private http: HttpClient,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.articleForm = this.fb.group({
      topicId: ['', Validators.required],

      title: ['', Validators.required],
      content: ['', Validators.required],
    });
    this.http
      .get<Topic[]>(`${environment.apiUrl}/topics`)
      .pipe(takeUntil(this.destroy$))
      .subscribe((data) => {
        this.topics = data;
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  onSubmit(): void {
    if (this.articleForm.valid) {
      const formValue = this.articleForm.value;

      const articleToCreate = {
        title: formValue.title,
        content: formValue.content,
        topicId: formValue.topicId,
      };

      this.articleService
        .createArticle(articleToCreate)
        .pipe(takeUntil(this.destroy$))
        .subscribe({
          next: () => {
            this.router.navigate(['/feed']);
          },
          error: (err) => {
            console.error('Erreur lors de la création de l’article', err);
          },
        });
    }
  }

  goBack(): void {
    this.location.back();
  }
}
