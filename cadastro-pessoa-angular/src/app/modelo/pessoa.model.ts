import { Sexo } from './enums/sexo';
import { Uf } from './enums/uf';

export class Pessoa {

    constructor(public id:number,
                public nome:string,
                public sexo: Sexo,
                public email: string,
                public data_nascimento: Date,
                public naturalidade: string,
                public nacionalidade: string,
                public cpf: string,
                public logradouro:string,
                public bairro: string,
                public cidade: string,
                public cep: string,
                public uf: Uf,
                public numero: number,
                public data_cadastro: Date,
                public data_atualizacao: Date){}
}