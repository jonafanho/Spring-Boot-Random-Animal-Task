import {Injectable, signal} from '@angular/core';
import {Client} from '@stomp/stompjs';
import {getUrl} from '../tool/utilities';
import SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  readonly animal = signal("");

  constructor() {
    const client = new Client({
      brokerURL: `${getUrl()}ws`,
      webSocketFactory: () => new SockJS(`${getUrl()}ws`),
      onConnect: () => {
        console.log("Websocket connected");
        client.subscribe('/topic/animals', message => this.animal.set(message.body));
      },
    });

    client.activate();
  }
}
