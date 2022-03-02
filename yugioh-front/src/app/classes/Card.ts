import { Deck } from "./deck";
import { RelDeckCards } from "./Rel_Deck_Cards";

export class Card {
    id:number;
	numero: number;
	categoria: string;
	nome: string;
	 nomePortugues:string
	 atributo:string
	 propriedade:string
    nivel:number;
	 tipos:[]
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
	 arquetipo:[]
	 qtd_link:string
	 genericType:string
	 set_decks: Deck[];
	 attributeImg:any;
	 isExtraDeck:boolean;
	 
	 price:number;
	 rarity:string;
	 relDeckCards: any
	 set_code = new Array();
	 card_set_code: string;
	 index:number
	 totalFound: number;
	 
    
}