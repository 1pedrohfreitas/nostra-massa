export class BebidaDTO {
    id : number = 0;
    nome: string = '';
    tamanhoValor: BebidaTamanhoValorDTO[] =[];
}
export class BebidaTamanhoValorDTO {
    tamanho: string = '';
    valor: number = 0;
}