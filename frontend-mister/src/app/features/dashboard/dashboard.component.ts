import { Component } from '@angular/core';
import { Player } from '../../models/player';
import { PlayerService } from '../../core/players/services/player.service';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { PlayerCreate } from '../player-create/player-create';
import { BlockLike } from 'typescript';
import { Alert } from '../../shared/alert/alert';
import { Confirm } from '../../shared/confirm/confirm';

@Component({
  selector: 'app-dashboard.component',
  imports: [
    CommonModule,
    FormsModule,
    MatDialogModule,
    Alert,
    Confirm
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
  alertMessage: string = '';
  showAlert: boolean = false;
  showConfirm: boolean= false;
  confirmMessage: string = '';
  confirmCallback: (() => void ) | null = null;
  

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
    this.askConfirmation('¿Seguro que quieres borrar el jugador?',()=>{
      this.playerService.deletePlayer(id).subscribe({
        next: ()=>{ 
          this.players = this.players.filter(p=> p.id !== id);
          this.showAlertFunction('Jugador borrado con exito')
      },
      error:()=> {
        this.showAlertFunction('Error al borrar el jugador')
      }
      })
    })
  }

  increaseNumbClau(player:Player){
    this.askConfirmation('¿Seguro que quieres aumentar el número de clausulazos de este jugador?',()=>{
      player.numberClau= player.numberClau+1;
      this.playerService.updatePlayer(player.id,player).subscribe({
        next: ()=>{
          this.loadPlayers();
          this.showAlertFunction('El nùmero de clausulazos del jugador ha aumentado')
        },
        error: () => {
          this.showAlertFunction('Error aumentando el número de clausulazos del jugador')
        }
      })
    })
  }

  showAlertFunction(message:string){
    this.alertMessage=message;
    this.showAlert = true;

    setTimeout(()=>{
      this.showAlert= false
    },3000);
  }

  askConfirmation(message:string, callback: ()=> void){
    this.confirmMessage = message;
    this.showConfirm=true;
    this.confirmCallback=callback
  }

  onConfirmResponse(result: boolean){
    this.showConfirm = false;
    if(result && this.confirmCallback){
      this.confirmCallback();
    }
  }


  handleImageError(event: Event) {
  const img = event.target as HTMLImageElement;
  img.src = 'assets/images/default.webp';
}
}
