import { Sexo } from './enums/sexo';

export class Pessoa {

    constructor(public id:number,
                public nome:string,
                public sexo: Sexo,
                public email: string,
                public data_nascimento: Date,
                public naturalidade: string,
                public nacionalidade: string,
                public cpf: string,
                public data_cadastro: Date,
                public data_atualizacao: Date,){}
}