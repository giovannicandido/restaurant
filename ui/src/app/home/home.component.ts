import { Component, OnInit } from '@angular/core';
import { HomeService } from './home.service';
import { Observable } from 'rxjs';
import { Restaurant } from '../_models/restaurant';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  restaurants: Observable<Restaurant[]>;

  constructor(private service: HomeService) { }

  ngOnInit(): void {
    this.restaurants = this.service.load();
  }

}
