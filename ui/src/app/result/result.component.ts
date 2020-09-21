import { Component, OnInit } from '@angular/core';
import { ResultService } from './result.service';
import { Result } from '../_models/result';
import { Observable, pipe, throwError } from 'rxjs';
import { ToastrService } from 'ngx-toastr';
import { catchError } from 'rxjs/operators';

@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.scss']
})
export class ResultComponent implements OnInit {
  results: Observable<Result[]>;

  constructor(private resultService: ResultService,
              private toastrService: ToastrService) { }

  ngOnInit(): void {
    this.load();
  }

  process(): void {
    this.resultService.process()
      .pipe(
        catchError(err => {
          this.toastrService.error(err.error.message);
          return throwError(err);
        })
      ).subscribe(
      r => this.toastrService.info('Result processed for this day')
    );
  }

  load(): void {
    this.results = this.resultService.load()
      .pipe(
        catchError(err => {
          this.toastrService.error(err.error.message);
          return throwError(err);
        })
      );
  }

}
