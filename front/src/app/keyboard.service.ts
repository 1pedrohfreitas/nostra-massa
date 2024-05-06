import { Injectable } from '@angular/core';
import { fromEvent, Observable } from 'rxjs';
import { filter } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class KeyboardService {
  constructor() {}

  preventF1Default(): Observable<KeyboardEvent> {
    return fromEvent<KeyboardEvent>(document, 'keydown').pipe(
      filter((event: KeyboardEvent) => event.key === 'F1'),
      filter((event: KeyboardEvent) => {
        event.preventDefault();
        return false;
      })
    );
  }
}
