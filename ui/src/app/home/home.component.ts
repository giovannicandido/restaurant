import { Component, OnInit } from '@angular/core';
import { HomeService } from './home.service';
import { Observable, throwError } from 'rxjs';
import { Restaurant } from '../_models/restaurant';
import { catchError } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  restaurants: Observable<Restaurant[]>;

  constructor(private service: HomeService, private toast: ToastrService) {
  }

  ngOnInit(): void {
    this.loadRestaurants();
  }

  vote(id: any) {
    this.service.vote(id)
      .pipe(
        catchError(err => {
          this.toast.error('Erro ao votar');
          return throwError(err);
        })
      )
      .subscribe(r => {
        this.loadRestaurants();
        this.toast.info('Voto computado com sucesso');
      });
  }

  loadRestaurants() {
    this.restaurants = this.service.load();
  }
}
