import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TaskService } from '../services/task.service';
import { ToastrService } from 'ngx-toastr';
import { Task } from '../model/task';

@Component({
  selector: 'app-update',
  templateUrl: './update.component.html',
  styleUrls: ['./update.component.css'],
})
export class UpdateComponent implements OnInit {
  id!: number;
  task!: Task;

  constructor(
    private taskService: TaskService,
    protected toast: ToastrService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getTask();
  }

  onUpdate(): void {
    this.taskService.update(this.task, this.id).subscribe({
      next: (message) => {
        this.toast.success(message.message, 'OK', {
          timeOut: 3000,
          positionClass: 'toast-top-center',
        });
        this.router.navigate(['']);
      },
      error: (err) => {
        this.toast.error(err.error.message, 'Error', {
          timeOut: 3000,
          positionClass: 'toast-top-center',
        });
        this.router.navigate(['']);
      },
    });
  }

  getTask() {
    this.id = this.activatedRoute.snapshot.params['id'];
    this.taskService.taskDetail(this.id).subscribe({
      next: (task) => {
        this.task = task;
      },
      error: (err) => {
        this.toast.error(err.error.message, 'Error', {
          timeOut: 3000,
          positionClass: 'toast-top-center',
        });
        this.router.navigate(['']);
      },
    });
  }
}
