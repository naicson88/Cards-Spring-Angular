import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Archetype } from 'src/app/classes/Archetype';

@Injectable({
  providedIn: 'root'
})
export class AchetypeService {

  archetype: Archetype;
  archetypeId: number;
  
  constructor(private http: HttpClient ) {}

  base_url = "http://localhost:8080/yugiohAPI"

public setArchetypeId(archetypeId:number) {
    this.archetypeId = archetypeId;
 }

 public getArchetypeId(){
   return this.archetypeId;
 }

  public getAllArchetypes(){
    return  this.http.get<Archetype[]>(this.base_url+"/arch/all");
  }

  public getArchetype(id:any){
    return this.http.get<Archetype[]>(this.base_url+`/arch/archetype/${id}`);
  }

  
}
