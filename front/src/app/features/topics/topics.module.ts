import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TopicsComponent } from './topics.component';
import { TopicsRoutingModule } from './topics-routing.module';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';


@NgModule({
  declarations: [TopicsComponent],
  imports: [
    CommonModule,
    TopicsRoutingModule,
    MatButtonModule,
    MatFormFieldModule,
    MatSelectModule,
    MatCardModule
  ]
})
export class TopicsModule {}
