
export  class Imagens {
    

    public dark: string  = "DARK";
    public fire: string  = "FIRE";
    public earth: string  = "EARTH";
    public water: string  = "WATER";
    public light: string  = "LIGHT";
    public wind: string  = "WIND";

    public dark_img: string  = "..\\..\\assets\\img\\outras\\DARK.png";
    public fire_img: string = "..\\..\\assets\\img\\outras\\FIRE.png";
    public earth_img: string  = "..\\..\\assets\\img\\outras\\TERRA.png";
    public water_img: string = "..\\..\\assets\\img\\outras\\WATER.png";
    public light_img: string  = "..\\..\\assets\\img\\outras\\LUZ.png";
    public wind_img: string = "..\\..\\assets\\img\\outras\\WIND.png";

    public spell: string = "Spell Card";
    public trap: string = "Trap Card";
    public continuous:string = "Continuous";
    public field:string = "Field";
    public quick:string = "Quick-Play";
    public counter:string = "Counter";
    public equip:string = "Equip";

    public spell_img: string = "..\\..\\assets\\img\\outras\\MAGIA.png";
    public trap_img: string = "..\\..\\assets\\img\\outras\\ARMADILHA.png";
    public continuous_img:string = "..\\..\\assets\\img\\outras\\Continuous.png";
    public field_img:string = "..\\..\\assets\\img\\outras\\Field.png";
    public quick_img:string = "..\\..\\assets\\img\\outras\\Quick.png";
    public counter_img:string = "..\\..\\assets\\img\\outras\\Counter.png";
    public equip_img:string = "..\\..\\assets\\img\\outras\\Equip.jpg";

    public aqua: string = "Aqua";
    public beast: string = "Beast";
    public beast_warrior:string = "Beast-Warrior";
    public cyberse:string = "Cyberse";
    public dinosaur:string = "Dinosaur";
    public divine_beast:string = "Divine-Beast";
    public dragon:string = "Dragon";

    public aqua_img: string = '..\\..\\assets\\img\\tiposMonstros\\Aqua.png';
    public beast_img: string = '..\\..\\assets\\img\\tiposMonstros\\Beast-DG.png';
    public beast_warrior_img: string =  '..\\..\\assets\\img\\tiposMonstros\\Beast-Warrior-DG.png';
    //  case 'Creator-God'  : return '..\\..\\assets\\img\\tiposMonstros\\Beast-Warrior-DG.png';
    public cyberse_img:string =  '..\\..\\assets\\img\\tiposMonstros\\Cyberse.PNG';
    public dinosaur_img:string = '..\\..\\assets\\img\\tiposMonstros\\Dinosaur-DG.png';
    public divine_beast_img:string = '..\\..\\assets\\img\\tiposMonstros\\Divine-Beast-DG.png';
    public dragon_img:string =  '..\\..\\assets\\img\\tiposMonstros\\Dragon-DG.png';

    public fairy: string = "Fairy";
    public fiend: string = "Fiend";
    public fish:string = "Fish";
    public insect:string = "Insect";
    public machine:string = "Machine";
    public plant:string = "Plant";
    public psychic:string = "Psychic";

    public fairy_img: string =  '..\\..\\assets\\img\\tiposMonstros\\Fairy-DG.png';
    public fiend_img: string = '..\\..\\assets\\img\\tiposMonstros\\Fiend-DG.png';
    public fish_img:string =  '..\\..\\assets\\img\\tiposMonstros\\Fish-DG.png';
    public insect_img:string = '..\\..\\assets\\img\\tiposMonstros\\Insect-DG.png';
    public machine_img:string =  '..\\..\\assets\\img\\tiposMonstros\\Machine-DG.png';
    public plant_img:string = '..\\..\\assets\\img\\tiposMonstros\\Plant-DG.png';
    public psychic_img:string = '..\\..\\assets\\img\\tiposMonstros\\Psychic-DG.png';

    public pyro: string = "Pyro";
    public reptile: string = "Reptile";
    public rock:string = "Rock";
    public sea_serpent:string = "Sea Serpent";
    public spellcaster:string = "Spellcaster";
    public thunder:string = "Thunder";
    public warrior:string = "Warrior";
    public winged_beast:string = "Winged Beast";
    public wyrm:string = "Wyrn";
    public zombie:string = "Zombie";

    public pyro_img: string = '..\\..\\assets\\img\\tiposMonstros\\Pyro-DG.png';
    public reptile_img: string = '..\\..\\assets\\img\\tiposMonstros\\Reptile-DG.png';
    public rock_img:string = '..\\..\\assets\\img\\tiposMonstros\\Rock-DG.png';
    public sea_serpent_img:string = '..\\..\\assets\\img\\tiposMonstros\\Sea_Serpent-DG.png';
    public spellcaster_img:string = '..\\..\\assets\\img\\tiposMonstros\\Spellcaster-DG.png';
    public thunder_img:string =  '..\\..\\assets\\img\\tiposMonstros\\Thunder-DG.png';
    public warrior_img:string = '..\\..\\assets\\img\\tiposMonstros\\Warrior-DG.png';
    public winged_beast_img:string = '..\\..\\assets\\img\\tiposMonstros\\Winged_Beast-DG.png';
    public wyrm_img:string = '..\\..\\assets\\img\\tiposMonstros\\Wyrm-DG.png';
    public zombie_img:string = '..\\..\\assets\\img\\tiposMonstros\\Zombie-DG.png';

    public monster: string = "Monster";
    public spellc: string = "Spell";
    public trapc:string = "Trap";
    public pendulum:string = "Pendulum";
    public xyz:string = "XYZ";
    public synchron:string = "Synchron";
    public fusion:string = "Fusion";
    public link:string = "Link";
    public ritual:string = "Ritual";
 

    public monster_img: string = '/../../assets/img/outras/monsterIcon.png';
    public spellc_img: string = '/../../assets/img/outras/magicIcon.png'
    public trapc_img:string = '/../../assets/img/outras/trapIcon.png'
    public pendulum_img:string = ' /../../assets/img/outras/pendulumIcon.png'
    public xyz_img:string =' /../../assets/img/outras/xyzIcon.png'
    public synchron_img:string = ' /../../assets/img/outras/syncronIcon.png'
    public fusion_img:string ='/../../assets/img/outras/fusionIcon.png'
    public link_img:string = ' /../../assets/img/outras/linkIcon.png'
    public ritual_img:string = '/../../assets/img/outras/ritualIcon.png';

    public back_img : string ='/../../assets/img/outras/back.png'

    
    
   public  mapCardsIcons(){
        let mapCardsIcons = new Map();
        mapCardsIcons.set("Monster", '/../../assets/img/outras/monsterIcon.png')
        mapCardsIcons.set("Spell", '/../../assets/img/outras/magicIcon.png')
        mapCardsIcons.set("Trap", '/../../assets/img/outras/trapIcon.png')
        mapCardsIcons.set("Pendulum", '/../../assets/img/outras/pendulumIcon.png')
        mapCardsIcons.set("XYZ", '/../../assets/img/outras/xyzIcon.png')
        mapCardsIcons.set("Synchron", '/../../assets/img/outras/syncronIcon.png')
        mapCardsIcons.set("Fusion", '/../../assets/img/outras/fusionIcon.png')
        mapCardsIcons.set("Link", '/../../assets/img/outras/linkIcon.png')
        mapCardsIcons.set("Ritual", '/../../assets/img/outras/ritualIcon.png')

        return mapCardsIcons;
    }

    
}