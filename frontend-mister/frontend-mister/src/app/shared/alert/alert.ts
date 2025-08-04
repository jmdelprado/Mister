import { T } from '@angular/cdk/keycodes';
import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-alert',
  imports: [CommonModule],
  templateUrl: './alert.html',
  styleUrl: './alert.css'
})
export class Alert {
  
  @Input() message : string = '';
  visible: boolean = true;

  close(){
    this.visible= false;
  }
}
