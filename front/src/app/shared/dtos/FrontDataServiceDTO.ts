import { BairroDTO, RuaDTO } from "../models/ClienteDTO";
import { PizzaSaborDTO } from "../models/PizzaDTO";

export class FrontDataServiceDTO{
    bairros : BairroDTO[] = [] ;
    ruas : RuaDTO[] = [] ;
    pizzasSabor : PizzaSaborDTO[] = [] ;
	telefones : string[] = []
}