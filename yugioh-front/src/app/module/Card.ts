import { Deck } from "./deck";

export class Card {
    id:number;
	numero: string;
	categoria: string;
	nome: string;
	 nomePortugues:string
	 atributo:string
	 propriedade:string
    nivel:number;
	 tipos:string
	 atk:number;
	 def:number;
	 condicao:string;
	 descricao:string
	 descricaoPortugues:string
	 imagem:string
	 raridade:string
	 escala:number;
	 descr_pendulum:string
	 descr_pendulum_pt:string
	 arquetipo:string
	 qtd_link:string
	 set_decks: Deck[];
	 
    
}