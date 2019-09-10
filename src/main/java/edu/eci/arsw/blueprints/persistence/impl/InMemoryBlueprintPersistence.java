/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.BlueprintsPersistence;
import java.security.Key;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 *
 * @author hcadavid
 */
@Service
public class InMemoryBlueprintPersistence implements BlueprintsPersistence{

    private final Map<Tuple<String,String>,Blueprint> blueprints=new HashMap<>();

    public InMemoryBlueprintPersistence() {
        //load stub data
        Point[] pts=new Point[]{new Point(140, 140),new Point(115, 115)};
        Blueprint bp1=new Blueprint("carlos", "prueba",pts);
        Blueprint bp2=new Blueprint("negro", "obra2",pts);
        Blueprint bp3=new Blueprint("juan", "iliada",pts);
        Blueprint bp4=new Blueprint("juan", "SDFSDF",pts);
        Blueprint bp5=new Blueprint("negro", "aasda",pts);
        blueprints.put(new Tuple<>(bp1.getAuthor(),bp1.getName()), bp1);
        blueprints.put(new Tuple<>(bp2.getAuthor(),bp2.getName()), bp2);
        blueprints.put(new Tuple<>(bp3.getAuthor(),bp3.getName()), bp3);
        blueprints.put(new Tuple<>(bp4.getAuthor(),bp4.getName()), bp4);
        blueprints.put(new Tuple<>(bp5.getAuthor(),bp5.getName()), bp5);
    }    
    
    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        if (blueprints.containsKey(new Tuple<>(bp.getAuthor(),bp.getName()))){
            throw new BlueprintPersistenceException("The given blueprint already exists: "+bp);
        }
        else{
            blueprints.put(new Tuple<>(bp.getAuthor(),bp.getName()), bp);
        } 
    }

    @Override
    public Blueprint getBlueprint(String author, String bprintname) throws BlueprintNotFoundException {
        return blueprints.get(new Tuple<>(author, bprintname));
    }

    @Override
    public Set<Blueprint> getBlueprintByAuthor(String author) throws BlueprintNotFoundException{
        Set<Blueprint> temporal = new HashSet<>();
        for(int i = 0 ; i < blueprints.values().size();i++){
            Blueprint bluT = (Blueprint) blueprints.values().toArray()[i];
            if(bluT.getAuthor().equals(author)){temporal.add(bluT);} 
        }
        if(temporal.isEmpty()){
            throw new BlueprintNotFoundException("The given author doesn´t exists: "+author);
        }
        return temporal;
    }

    @Override
    public Set<Blueprint> getAllBlueprints() {
        Set<Blueprint> temporal = new HashSet<>();
        for(int i = 1 ; i < blueprints.values().size();i++){
            Blueprint bluT = (Blueprint) blueprints.values().toArray()[i];
            temporal.add(bluT);
        }
        return temporal;
    }
}
