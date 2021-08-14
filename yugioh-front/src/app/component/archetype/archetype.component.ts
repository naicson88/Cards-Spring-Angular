import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Archetype } from 'src/app/classes/Archetype';
import { AchetypeService } from 'src/app/service/archetype-service/achetype.service';


@Component({
  selector: 'app-archetype',
  templateUrl: './archetype.component.html',
  styleUrls: ['./archetype.component.css']
})
export class ArchetypeComponent implements OnInit {

  constructor(private router: Router, private service: AchetypeService, private archService: AchetypeService) { }

  ngOnInit() {
    this.loadAllArchetypes();
  }

  archetype: Archetype[] = [];

  loadAllArchetypes(){
    this.service.getAllArchetypes().subscribe(data => {
    this.archetype = data;

    })
  }

  goToLetter(ele:string){
  
    var elemento = document.getElementById(ele);
    elemento.scrollIntoView({
      behavior: "smooth",
      block: "start",
      inline : "nearest"
    })
    elemento.style.color = 'red'
  }

  
  storedArchetype(event){
    //const id = event.target.id;
    //localStorage.setItem("idArchetype", id);
   // console.log(id);
   const archId = event.target.id;
   if(archId != null && archId != ""){
     console.log(archId)
     this.archService.setArchetypeId(archId);
   
   } else {
      console.log("Unable to consult this card, try again later.");
      return false;
   }
  }

}
