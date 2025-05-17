import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user.model';
import { TopicResponse } from 'src/app/models/topic.model';
import { UserService } from 'src/app/services/user.service';
import { TopicService } from 'src/app/services/topic.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
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

  ngOnInit(): void {
    this.userService.getProfile().subscribe({
      next: (data) => {
        this.user = { ...data, password: '' };

        this.topicService.getAllTopicsWithStatus().subscribe({
          next: (topics) => {
            this.subscriptions = topics.filter((topics) => topics.subscribed);
          },
        });
      },

      error: (err) => {
        console.error('Erreur lors du chargement du profil : ', err);
        alert('Impossible de charger les données du profil.');
      },
    });
  }

  saveProfile(): void {
    this.message = '';
    this.userService.updateUser(this.user).subscribe({
      next: (response) => {
        const newToken = response.token;
        console.log('>>> Новый токен: ', response.token);
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
    this.topicService.unsubscribeFromTopic(topicId).subscribe({
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
