import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TopicsComponent } from './topics.component';
import { FeedComponent } from '../feed/feed.component';

const routes: Routes = [
  { path: '', component: TopicsComponent },
  { path: 'topic/:topicId/articles', component: FeedComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TopicsRoutingModule {}
