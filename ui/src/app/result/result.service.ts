import { Injectable, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Restaurant } from '../_models/restaurant';
import { Result } from '../_models/result';

const VOTE_RESULT_API_URL = `${environment.apiUrl}/vote-result/`;
const PROCESS_VOTE_URL = `${environment.apiUrl}/vote-result/process/`;


@Injectable({
  providedIn: 'root'
})
export class ResultService {

  constructor(private http: HttpClient) {
  }

  load(): Observable<Result[]> {
    return this.http.get<Result[]>(VOTE_RESULT_API_URL);
  }

  process(): Observable<any> {
    return this.http.post(PROCESS_VOTE_URL, null);
  }
}
