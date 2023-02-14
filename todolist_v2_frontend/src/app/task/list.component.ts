import { Component, OnInit } from '@angular/core';
import { TaskService } from '../services/task.service';
import { ToastrService } from 'ngx-toastr';
import { Task } from '../model/task';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css'],
})
export class ListComponent implements OnInit {
  public tasks: Task[] = [];

  constructor(private taskService: TaskService, private toast: ToastrService) {}

  ngOnInit(): void {
    this.getTasks();
  }

  getTasks(): void {
    this.taskService.listTasks().subscribe({
      next: (tasks) => {
        this.tasks = tasks;
      },
      error: (err) => {
        this.toast.error(err.error.message, 'Error', {
          timeOut: 3000,
          positionClass: 'toast-top-center',
        });
      },
    });
  }

  onDelete(id: number): void {
    Swal.fire({
      title: 'Are you sure?',
      text: 'You cannot undo it',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'OK',
      cancelButtonText: 'Cancel',
    }).then((result) => {
      if (result.value) {
        this.taskService.delete(id).subscribe({
          next: (message) => {
            this.toast.success(message.message, 'OK', {
              timeOut: 3000,
              positionClass: 'toast-top-center',
            });
            this.getTasks();
          },
          error: (err) => {
            this.toast.error(err.error.message, 'Error', {
              timeOut: 3000,
              positionClass: 'toast-top-center',
            });
          },
        });
      } else if (result.dismiss === Swal.DismissReason.cancel) {
        Swal.fire('Canceled', 'Task has not been deleted', 'error');
      }
    });
  }
}
