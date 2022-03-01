import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminDashboardComponent } from './component/admin/admin-dashboard/admin-dashboard.component';
import { ArchetypeDetailsComponent } from './component/archetype-details/archetype-details/archetype-details.component';
import { ArchetypeComponent } from './component/archetype/archetype.component';
import { CardDetailComponent } from './component/card-detail/card-detail/card-detail.component';
import { CardsSearchComponent } from './component/cards-search/cards-search/cards-search.component';
import { SearchBoxComponent } from './component/cards-search/search-box/search-box.component';
import { DeckDetailUserComponent } from './component/deck-detail-user/deck-detail-user.component';
import { DeckDetailComponent } from './component/deck-detail/deck-detail.component';
import { DeckComponent } from './component/deck/deck.component';
import { HomeComponent } from './component/home/home.component';
import { IndexComponent } from './component/index/index.component';
import { LoginComponent } from './component/index/login/login.component';
import { RegisterComponent } from './component/index/register/register.component';
import { UsercardsComponent } from './component/usercards/usercards.component';
import { ErrorPagesComponent } from './error-pages/error-pages/error-pages.component';
import { BaseRoleGuard } from './Util/CanActivate/BaseRoleGuard';

const routes: Routes = [
  { path: 'decks', component: DeckComponent , data : {set_type: 'D'}},
  { path: 'tins', component: DeckComponent , data : {set_type: 'T'}},
  { path: 'boxes', component: DeckComponent , data : {set_type: 'B'}},
  { path: 'usercollection/decks', component: DeckComponent , data : {set_type: 'UD'}},
  { path: 'usercollection/cards', component: UsercardsComponent },
  { path: 'home', component: HomeComponent },
  { path: 'deck-details/:deckName', component: DeckDetailComponent},
  { path: 'user-deck-details/:deckName', component: DeckDetailComponent, data: {source: 'U'}},
  { path: 'card-detail/:cardName', component: CardDetailComponent },
  { path: 'index', component: IndexComponent },
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'archetypes', component: ArchetypeComponent},
  { path: 'archetypeDetails/:archId', component: ArchetypeDetailsComponent},
  { path: 'cards-search', component: CardsSearchComponent},
  { path: 'userdeck-details/:deckName', component: DeckDetailUserComponent },
  { path: 'search-box', component: SearchBoxComponent},
  { path: 'error-page/:code', component:ErrorPagesComponent},
  { path: 'admin-dashboard', component:AdminDashboardComponent, canActivate: [BaseRoleGuard]},
  { path: '', redirectTo: 'index', pathMatch: 'full' },
  
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }