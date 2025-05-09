import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { AuthGuard } from './features/auth/auth.guard';


// consider a guard combined with canLoad / canActivate route option
// to manage unauthenticated user to access private routes
const routes: Routes = [{ path: '', component: HomeComponent },
  {path:'', 
    loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule)
  },
  
  {path:'feed', 
    loadChildren: () => import('./features/feed/feed.module').then(m => m.FeedModule),
    canActivate: [AuthGuard]
  },

  {path:'articles', 
    loadChildren: () => import('./features/articles/article.module').then(m => m.ArticleModule),
    canActivate: [AuthGuard]
  },

  {
    path: 'topics',
    loadChildren: () => import('./features/topics/topics.module').then(m => m.TopicsModule),
    canActivate: [AuthGuard]
  },

  { path: 'profile', loadChildren: () => import('./features/profile/profile.module').then(m => m.ProfileModule),
    canActivate: [AuthGuard]
    
  },

  { path: '**', redirectTo: '' } 

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
