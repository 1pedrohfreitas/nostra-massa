export class PizzaSaborDTO {
    id : number = 0;
    idSabor: number = 0;
    nome: string = '';
    tamanhoGigante : number = 0;
    tamanhoGrande : number = 0;
    tamanhoMedia : number = 0;
    pizzasAcrescimos : PizzaAcrescimoDTO[] = [];
    pizzaSaborIngredientes : PizzaSaborIngredienteDTO [] = [];
    pizzaSaborIngredientesRetirar : PizzaSaborIngredienteDTO [] = [];
}

export class PizzaAcrescimoDTO {
    id: number = 0;
    nome: string = '';
    habilitado :boolean = false;
	tamanhoMediaMetade ?: number = 0;
	tamanhoGrandeMetade ?: number = 0;
	tamanhoGiganteMetade ?: number = 0;
	tamanhoMediaToda ?: number = 0;
	tamanhoGrandeToda ?: number = 0;
	tamanhoGiganteToda ?: number = 0;
}
export class PizzaSaborIngredienteDTO {
    id: number = 0;
    nome : string = '';
    habilitado :boolean = false;
}