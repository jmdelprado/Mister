import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { PlayerService } from '../../core/players/services/player.service';
import { Player } from '../../models/player';

@Component({
  selector: 'app-player-create',
  standalone: true,
  imports: [
    CommonModule, 
    FormsModule
  ],
  templateUrl: './player-create.html',
  styleUrl: './player-create.css'
})
export class PlayerCreate {

  player: Player = {
    id: 0,
    name: '',
    lastname: '',
    numberClau: 1,
    date: new Date(),
    image: ''
  };

  selectedFile: File | null = null;
  isLoading = false;
  errorMessage = '';
  maxFileSize = 2 * 1024 * 1024; // 2MB

  constructor(
    private playerService: PlayerService, 
    private dialogRef: MatDialogRef<PlayerCreate>
  ) {}

  onImageSelected(event: any) {
    const file: File = event.target.files[0];
    if (file) {
      // Validar el tamaño del archivo
      if (file.size > this.maxFileSize) {
        this.errorMessage = 'La imagen no puede ser mayor a 2MB';
        this.selectedFile = null;
        this.player.image = '';
        return;
      }

      // Validar el tipo de archivo
      if (!file.type.startsWith('image/')) {
        this.errorMessage = 'El archivo debe ser una imagen';
        this.selectedFile = null;
        this.player.image = '';
        return;
      }

      this.selectedFile = file;
      this.errorMessage = '';
      
      // Crear preview de la imagen
      const reader = new FileReader();
      reader.onload = (e: any) => {
        this.player.image = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  }

  onSubmit(form: any) {
    if (form.valid && this.selectedFile) {
      this.isLoading = true;
      this.errorMessage = '';

      const dateObj = this.player.date instanceof Date ? this.player.date : new Date(this.player.date);

      const formData = new FormData();
      formData.append('name', this.player.name);
      formData.append('lastname', this.player.lastname);
      formData.append('numberClau', this.player.numberClau.toString());
      formData.append('date', dateObj.toISOString().substring(0, 10));
      formData.append('image', this.selectedFile);

      this.playerService.createPlayer(formData).subscribe({
        next: (response) => {
          console.log('Jugador creado exitosamente:', response);
          this.dialogRef.close(true);
        },
        error: (error) => {
          console.error('Error al crear jugador:', error);
          this.errorMessage = 'Error al crear el jugador. Por favor, inténtalo de nuevo.';
          this.isLoading = false;
        }
      });
    } else if (!this.selectedFile) {
      this.errorMessage = 'Por favor, selecciona una imagen.';
    }
  }

  onCancel() {
    this.dialogRef.close(false);
  }
}
