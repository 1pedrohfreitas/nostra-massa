import { Component } from '@angular/core';
import { SharedModule } from '../../../shared/shared.module';

@Component({
  selector: 'app-pizzas',
  standalone: true,
  imports: [SharedModule],
  templateUrl: './pizzas.component.html',
  styleUrl: './pizzas.component.scss'
})
export class PizzasComponent {

}
