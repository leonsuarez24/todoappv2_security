import { Component, OnInit } from '@angular/core';
import { TaskService } from '../services/task.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { Task } from '../model/task';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css'],
})
export class CreateComponent implements OnInit {
  name!: string;
  content!: string;

  constructor(
    private taskService: TaskService,
    protected toast: ToastrService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  onCreate(): void {
    const task: Task = new Task(this.name, this.content);
    this.taskService.create(task).subscribe({
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
}
