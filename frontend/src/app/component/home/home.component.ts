import {Component, inject} from '@angular/core';
import {ApiService} from '../../service/api.service';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatButtonModule} from '@angular/material/button';
import {WebsocketService} from '../../service/websocket.service';

@Component({
  selector: 'app-home',
  imports: [
    MatButtonModule,
    MatProgressSpinnerModule,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {
  private readonly apiService = inject(ApiService);
  private readonly websocketService = inject(WebsocketService);
  readonly loading = this.apiService.loading;
  readonly running = this.apiService.running;
  readonly errorMessage = this.apiService.errorMessage;
  readonly animal = this.websocketService.animal;

  start() {
    this.apiService.start();
  }

  stop() {
    this.apiService.stop();
  }
}
