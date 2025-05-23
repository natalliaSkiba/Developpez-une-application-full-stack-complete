import { Component, OnInit, OnDestroy } from '@angular/core';
import { User } from 'src/app/models/user.model';
import { TopicResponse } from 'src/app/models/topic.model';
import { UserService } from 'src/app/services/user.service';
import { TopicService } from 'src/app/services/topic.service';
import { Subject, takeUntil } from 'rxjs';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit, OnDestroy {
  constructor(
    private userService: UserService,
    private topicService: TopicService
  ) {}
  user: User = {
    username: '',
    email: '',
    password: '',
  };
  message = '';
  isError = true;

  subscriptions: TopicResponse[] = [];
  private destroy$ = new Subject<void>();

  ngOnInit(): void {
    this.userService
      .getProfile()
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (data) => {
          this.user = { ...data, password: '' };

          this.topicService
            .getAllTopicsWithStatus()
            .pipe(takeUntil(this.destroy$))
            .subscribe({
              next: (topics) => {
                this.subscriptions = topics.filter(
                  (topics) => topics.subscribed
                );
              },
            });
        },

        error: (err) => {
          console.error('Erreur lors du chargement du profil : ', err);
          alert('Impossible de charger les données du profil.');
        },
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  saveProfile(): void {
    this.message = '';
    this.userService
      .updateUser(this.user)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: (response) => {
          const newToken = response.token;
          console.log(' new token: ', response.token);
          localStorage.setItem('token', newToken);
          this.message = 'Profil mis à jour avec succès !';
          this.isError = false;
          this.user.password = '';
        },
        error: (err) => {
          console.error('Erreur lors de la mise à jour du profil.', err);
          this.message = err.error?.message || 'Erreur inconnue du serveur.';
        },
      });
  }

  unsubscribe(topicId: number): void {
    this.topicService
      .unsubscribeFromTopic(topicId)
      .pipe(takeUntil(this.destroy$))
      .subscribe({
        next: () => {
          this.subscriptions = this.subscriptions.filter(
            (sub) => sub.id !== topicId
          );
        },
        error: (err) => {
          console.error('Erreur lors du désabonnement :', err);
          alert('Une erreur est survenue lors du désabonnement.');
        },
      });
  }
}
