import { Card } from "./Card";
import { RelDeckCards } from "./Rel_Deck_Cards";


export class Deck {
    id: number;
    imagem: string;
    nome: string
    nomePortugues: string
    cards: Card[];
    isKonamiDeck:string;
    lancamento:Date;
    setType:string;
    qtdCommon:number = 0;
    qtdRare:number = 0;
    qtdSuperRare:number = 0;
    qtdUltraRare:number = 0;
    rel_deck_cards:RelDeckCards[]
}