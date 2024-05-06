export class BebidaDTO {
    nome?: string;
    tamanhoValor?: BebidaTamanhoValorDTO[];
}
export class BebidaTamanhoValorDTO {
    tamanho?: string;
    valor?: number;
}