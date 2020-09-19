import { Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Restaurant } from '../_models/restaurant';

const RESTAURANT_API_URL = `${environment.apiUrl}/restaurant/`;
const VOTE_API_URL = `${environment.apiUrl}/vote/`;


@Injectable({
  providedIn: 'root'
})
export class HomeService {

  constructor(private http: HttpClient) {

  }

  load(): Observable<Restaurant[]> {
    return this.http.get<Restaurant[]>(RESTAURANT_API_URL);
  }

  vote(id: number) {
    return this.http.post(VOTE_API_URL, id);
  }
}
