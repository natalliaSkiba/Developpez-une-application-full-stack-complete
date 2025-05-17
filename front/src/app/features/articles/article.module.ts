import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { CreateComponent } from './create/create.component';
import { ArticleRoutingModule } from './article-routing.module';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';

import { MatOptionModule } from '@angular/material/core';
import { ArticleDetailsComponent } from './article-details/article-details.component';


@NgModule({
  declarations: [CreateComponent, ArticleDetailsComponent],
  imports: [
    CommonModule,
    ArticleRoutingModule,
    MatButtonModule,
    MatFormFieldModule,
    MatSelectModule,
    MatIconModule,
    ReactiveFormsModule,
    MatInputModule,
    MatOptionModule,
    FormsModule
  ]
})
export class ArticleModule {}
