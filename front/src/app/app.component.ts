import { ChangeDetectorRef, Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';
import { KeyboardService } from './keyboard.service';
import { GetDadosServiceService } from './services/get-dados-service.service';
import { LocalStorageServiceService } from './services/local-storage-service.service';
import { NotificacoesComponent } from './componentes/notificacoes/notificacoes.component';
import { ApiServicesService } from './services/api-services.service';
import { Notificacao } from './shared/models/RestResponse';
import { Menu, MenuDTO } from './shared/MenuDTO';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet,RouterLink, RouterLinkActive, NotificacoesComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'Nostra Massa';
  notificacoes : Notificacao[] = [];

  menuItem = Menu
  menuSelecionado : MenuDTO = new MenuDTO
  showSubMenu = false;

  constructor(
    private keyboardService: KeyboardService,
    private getDadosService: GetDadosServiceService,
    private _localStorageService : LocalStorageServiceService,
    private _apiService : ApiServicesService,
    private cdRef: ChangeDetectorRef
    ) {}

  ngOnInit(): void {
    this.keyboardService.preventF1Default().subscribe();
    this.getDadosService.getDadosGeral();
    this._localStorageService.handleLocalStorageDataFront();
    this._apiService.imprimiNotificacaoAppComponent.subscribe((item)=>{
      this.adicionaNotificacao(item)
    });

  }
  adicionaNotificacao(notificacao : Notificacao){
    this.notificacoes.push(notificacao);
    this.cdRef.markForCheck();
  }

  selecionaSubMenu(menuSecionado : MenuDTO | undefined){
    if(menuSecionado != undefined && menuSecionado.children != undefined){
      this.menuSelecionado = menuSecionado
      this.showSubMenu = true
    } else {
      this.menuSelecionado = new MenuDTO
      this.showSubMenu = false
    }
  }
  
}
