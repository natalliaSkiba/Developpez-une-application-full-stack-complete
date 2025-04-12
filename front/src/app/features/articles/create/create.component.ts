import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { ArticleService } from 'src/app/services/article.service';
import { Route, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Topic } from 'src/app/models/topic.model';

@Component({
  selector: 'app-create-article',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.scss']
})
export class CreateComponent implements OnInit {
  articleForm!: FormGroup;
  topics: Topic[] = [];

   constructor(
    private fb: FormBuilder, 
    private location: Location,
    private articleService: ArticleService,
    private http:HttpClient,
    private router: Router) {}

  ngOnInit(): void {
    this.articleForm = this.fb.group({
      topicId: ['', Validators.required],
      
      title: ['', Validators.required],
      content: ['', Validators.required]
    });
    this.http.get<Topic[]>('http://localhost:8080/topics')
    .subscribe(data => {
      this.topics = data;
    });
  }

  onSubmit(): void {
    if (this.articleForm.valid) {
      console.log('Нажали кнопку создать');
      console.log('Значение формы:', this.articleForm.value);
    
      const formValue = this.articleForm.value;
  
      const articleToCreate = {
        title: formValue.title,
        content: formValue.content,
        topicId: formValue.topicId,
        authorId: 1, // заменим позже на текущего пользователя
      };
  
      this.articleService.createArticle(articleToCreate).subscribe({
        next: () => {
          this.router.navigate(['/feed']);
        },
        error: (err) => {
          console.error('Erreur lors de la création de l’article', err);
        }
      });
    }
  }

  goBack(): void {
    this.location.back();
  }
}
