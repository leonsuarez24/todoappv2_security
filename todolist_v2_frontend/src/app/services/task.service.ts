import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Task } from '../model/task';

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  url: string = 'http://localhost:8080/api/todolist';

  constructor(private http: HttpClient) {}

  public listTasks(): Observable<Task[]> {
    return this.http.get<Task[]>(this.url);
  }

  public taskDetail(id: number): Observable<Task> {
    return this.http.get<Task>(`${this.url}/${id}`);
  }

  public create(task: Task): Observable<any> {
    return this.http.post<any>(this.url, task);
  }

  public update(task: Task, id: number): Observable<any> {
    return this.http.put<any>(`${this.url}/${id}`, task);
  }

  public delete(id: number): Observable<any> {
    return this.http.delete<any>(`${this.url}/${id}`);
  }
}
