import { Component } from '@angular/core';
import { Player } from '../../models/player';
import { PlayerService } from '../../core/players/services/player.service';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { PlayerCreate } from '../player-create/player-create';
import { BlockLike } from 'typescript';

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
  isAdmin = JSON.parse(localStorage.getItem('isAdmin') || 'false');

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

  deletePlayer(id:number){
    if(confirm('Seguro que quieres borrar el jugador')){
      this.playerService.deletePlayer(id).subscribe({
        next: ()=>{ 
          this.players = this.players.filter(p=> p.id !== id);
          alert('Jugador borrado con exito')
      },
      error:()=> {
        alert('Error al borrar el jugador')
      }
      })
    }
  }
  increaseNumbClau(player:Player){
    if(confirm('Seguro que quieres aumentar el número de clausulazos de este jugador')){
      player.numberClau= player.numberClau+1;
      this.playerService.updatePlayer(player.id,player).subscribe({
        next: ()=>{
          this.loadPlayers();
          alert('El nùmero de clausulazos del jugador ha aumentado')
        },
        error: () => {
          alert('Error aumentando el número de clausulazos del jugador')
        }
      })
    }
  }
}
