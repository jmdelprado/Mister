import { Component } from '@angular/core';
import { Player } from '../../models/player';
import { PlayerService } from '../../core/players/services/player.service';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { PlayerCreate } from '../player-create/player-create';

@Component({
  selector: 'app-dashboard.component',
  imports: [
    CommonModule,
    FormsModule,
    MatDialogModule
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {

  players: Player[] = [];
  searchTerm: string = '';
  errorMessage: string = '';
  isLoading = false;

  constructor(private playerService: PlayerService, private dialog: MatDialog) {}

  ngOnInit(): void {
    this.loadPlayers();
  }

  loadPlayers() {
    this.isLoading = true;
    this.errorMessage = '';
    
    this.playerService.getPlayers().subscribe({
      next: (data) => {
        this.players = data;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error al cargar jugadores:', error);
        this.errorMessage = 'Error al cargar los jugadores';
        this.isLoading = false;
      }
    });
  }

  search() {
    if (this.searchTerm.trim() === '') {
      this.loadPlayers();
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    this.playerService.searchPlayers(this.searchTerm).subscribe({
      next: (data) => {
        this.players = data;
        this.isLoading = false;
      },
      error: (error) => {
        console.error('Error al buscar jugadores:', error);
        this.errorMessage = 'No se han encontrado jugadores';
        this.isLoading = false;
      }
    });
  }

  openCreatePlayerModal() {
    const dialogRef = this.dialog.open(PlayerCreate, {
      width: '600px',
      maxWidth: '90vw',
      maxHeight: '90vh',
      disableClose: true,
      panelClass: 'custom-modal-panel'
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        // Jugador creado exitosamente, recargar la lista
        this.loadPlayers();
      }
    });
  }

  clearSearch() {
    this.searchTerm = '';
    this.loadPlayers();
  }
}
