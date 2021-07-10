import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ArchetypeDetailsComponent } from './component/archetype-details/archetype-details/archetype-details.component';
import { ArchetypeComponent } from './component/archetype/archetype.component';
import { CardDetailComponent } from './component/card-detail/card-detail/card-detail.component';
import { DeckDetailComponent } from './component/deck-detail/deck-detail.component';
import { DeckComponent } from './component/deck/deck.component';
import { HomeComponent } from './component/home/home.component';
import { IndexComponent } from './component/index/index.component';
import { LoginComponent } from './component/index/login/login.component';
import { RegisterComponent } from './component/index/register/register.component';



const routes: Routes = [
  { path: 'decks', component: DeckComponent },
  { path: 'home', component: HomeComponent },
  { path: 'deck-details/:deckName', component: DeckDetailComponent},
  { path: 'card-detail/:cardName', component: CardDetailComponent },
  { path: 'index', component: IndexComponent },
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'archetypes', component: ArchetypeComponent},
  { path: 'archetypeDetails/:archId', component: ArchetypeDetailsComponent},
  { path: '', redirectTo: 'index', pathMatch: 'full' },
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }