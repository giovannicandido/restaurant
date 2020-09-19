import { Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Restaurant } from '../_models/restaurant';

const RESTAURANT_API_URL = `${environment.apiUrl}/restaurant/`;


@Injectable({
  providedIn: 'root'
})
export class HomeService {

  constructor(private http: HttpClient) {

  }

  load(): Observable<Restaurant[]> {
    return this.http.get<Restaurant[]>(RESTAURANT_API_URL);
  }

}
