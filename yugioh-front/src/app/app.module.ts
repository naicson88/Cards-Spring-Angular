import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { DeckComponent } from './component/deck/deck.component';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { SideMenuComponent } from './component/deck/side-menu/side-menu.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {IvyCarouselModule} from 'angular-responsive-carousel';
import {NgxPaginationModule} from 'ngx-pagination';
import { HomeComponent } from './component/home/home.component'; // <-- import the module
import { FilterPipeModule } from 'ngx-filter-pipe';
import { FormsModule, ReactiveFormsModule  }   from '@angular/forms';
import { DeckDetailComponent } from './component/deck-detail/deck-detail.component';
import { CardDetailComponent } from './component/card-detail/card-detail/card-detail.component';
import { IndexComponent } from './component/index/index.component';
import { LoginComponent } from './component/index/login/login.component';

import { RegisterComponent } from './component/index/register/register.component';
import { FooterComponent } from './component/footer/footer.component';
import { authStrategyProvider } from './service/auth-service/auth.strategy';
import { AuthInterceptor } from './service/auth-service/AuthInterceptor';
import { ArchetypeComponent } from './component/archetype/archetype.component';
import { ArchetypeDetailsComponent } from './component/archetype-details/archetype-details/archetype-details.component';
import { ImageToolTip } from './Util/ImageToolTip';
import {  MatSpinner } from '@angular/material';
import { ToastrModule } from 'ngx-toastr';
import { NgxSpinnerModule } from "ngx-spinner";
import { SpinnerComponent } from './component/spinner/spinner.component';
import { SpinnerService } from './service/spinner.service';
import { CardsSearchComponent } from './component/cards-search/cards-search/cards-search.component';
import { Imagens } from './classes/Imagens';
import { MaterialModule } from './Util/materialModule/material/material.module';
import { UsercardsComponent } from './component/usercards/usercards.component';
import { SideCardsearchComponent } from './component/cards-search/side-cardsearch/side-cardsearch.component';


@NgModule({
  declarations: [
    AppComponent,
    DeckComponent,
    SideMenuComponent,
    HomeComponent,
    DeckDetailComponent,
    CardDetailComponent,
    IndexComponent,
    LoginComponent,
    RegisterComponent,
    FooterComponent,
    ArchetypeComponent,
    ArchetypeDetailsComponent,
    SpinnerComponent,
    MatSpinner,
    CardsSearchComponent,
    UsercardsComponent,
    SideCardsearchComponent

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    NgxPaginationModule ,
    IvyCarouselModule,
    FilterPipeModule,
    FormsModule,
    ReactiveFormsModule ,
    NgxSpinnerModule,
    MaterialModule,
    ToastrModule.forRoot()

  ],
  providers: [
    authStrategyProvider, 
  {provide: HTTP_INTERCEPTORS,
  useClass: AuthInterceptor,
  multi: true},
  ImageToolTip,
  {provide: HTTP_INTERCEPTORS,useClass: SpinnerService, multi: true },
  Imagens
],
  bootstrap: [AppComponent]
})
export class AppModule { }
