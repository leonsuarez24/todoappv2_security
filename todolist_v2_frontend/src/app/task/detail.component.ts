import { Component, OnInit } from '@angular/core';
import { TaskService } from '../services/task.service';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Router } from '@angular/router';
import { Task } from '../model/task';

@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css'],
})
export class DetailComponent implements OnInit {
  public task: Task | undefined;

  constructor(
    private taskService: TaskService,
    protected toast: ToastrService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.getTask();
  }

  getTask() {
    const id = this.activatedRoute.snapshot.params['id'];
    this.taskService.taskDetail(id).subscribe({
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
