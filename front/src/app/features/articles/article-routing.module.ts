import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CreateComponent } from './create/create.component'; 
import { ArticleDetailsComponent } from './article-details/article-details.component';

const routes: Routes = [
  { path: 'create', component: CreateComponent },
  { path: ':id', component: ArticleDetailsComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ArticleRoutingModule {}
