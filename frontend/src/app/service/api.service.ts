import {inject, Injectable, signal} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {getUrl} from '../tool/utilities';

@Injectable({providedIn: 'root'})
export class ApiService {
  private readonly httpClient = inject(HttpClient);

  /**
   * Used by the UI to show whether an http request is currently happening.
   */
  public readonly loading = signal(false);
  /**
   * Used by the UI to show whether the backend service is currently running.
   */
  public readonly running = signal(false);
  /**
   * Used by the UI to show any HTTP errors. If there are no errors, this returns undefined.
   */
  public readonly errorMessage = signal<string | undefined>(undefined);

  constructor() {
    this.get("api/status");
  }

  start() {
    this.get("api/start");
  }

  stop() {
    this.get("api/stop");
  }

  /**
   * Run a GET request. Assume all responses are boolean values representing the state of the service. Caching is disabled; we always want fresh data.
   * @param endpoint the endpoint to call
   */
  private get(endpoint: string) {
    this.loading.set(true);
    this.errorMessage.set(undefined);
    this.httpClient.get<boolean>(`${getUrl()}${endpoint}`, {headers: {'Cache-Control': 'no-cache'}}).subscribe({
      next: running => {
        this.loading.set(false);
        this.running.set(running);
        this.errorMessage.set(undefined);
      },
      error: error => {
        this.loading.set(false);
        this.running.set(false);
        this.errorMessage.set(error.message);
      },
    });
  }
}
