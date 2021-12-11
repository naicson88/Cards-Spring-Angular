import { Card } from "./Card";


export class Deck {
    id: number;
    imagem: string;
    nome: string
    nomePortugues: string
    cards: Card[];
    isKonamiDeck:string;
    lancamento:Date;
    setType:string
}