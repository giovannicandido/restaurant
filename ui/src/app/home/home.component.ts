import { Component, OnInit } from '@angular/core';
import { HomeService } from './home.service';
import { Observable, throwError } from 'rxjs';
import { Restaurant } from '../_models/restaurant';
import { catchError, finalize, tap } from 'rxjs/operators';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  restaurants: Observable<Restaurant[]>;
  loadError = null;
  loading = false;

  constructor(private service: HomeService, private toast: ToastrService) {
  }

  ngOnInit(): void {
    this.loadRestaurants();
  }

  vote(id: any) {
    this.service.vote(id)
      .pipe(
        catchError(err => {
          this.toast.error(err.error.message, 'Vote error');
          return throwError(err);
        })
      )
      .subscribe(r => {
        this.loadRestaurants();
        this.toast.info('Vote computed with success');
      });
  }

  loadRestaurants() {
    this.loading = true;
    this.restaurants = this.service.load()
      .pipe(
        finalize(() => this.loading = false),
        catchError(err => {
          this.loadError = err;
          this.loading = false;
          return throwError(err);
        })
      );
  }
}
