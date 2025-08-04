import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-confirm',
  imports: [],
  templateUrl: './confirm.html',
  styleUrl: './confirm.css'
})
export class Confirm {
  @Input() message : string  = '';
  @Output() confirmed = new EventEmitter<boolean>();

  confirm(){
    this.confirmed.emit(true);
  }
  cancel(){
    this.confirmed.emit(false);
  }
}
