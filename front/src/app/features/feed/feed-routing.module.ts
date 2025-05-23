import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FeedComponent } from './feed.component';

const routes: Routes = [
  { path: '', component: FeedComponent },
  { path: 'topic/:topicId/articles', component: FeedComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FeedRoutingModule {}
