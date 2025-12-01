import {Component} from '@angular/core';
import {HomeComponent} from './component/home/home.component';

@Component({
  selector: 'app-root',
  imports: [
    HomeComponent
  ],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
}
