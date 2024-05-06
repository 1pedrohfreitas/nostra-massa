import { Component } from '@angular/core';
import { RouterLink, RouterLinkActive, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-page-produtos',
  standalone: true,
  imports: [RouterOutlet,RouterLink, RouterLinkActive],
  templateUrl: './page-produtos.component.html',
  styleUrl: './page-produtos.component.scss'
})
export class PageProdutosComponent {

}
