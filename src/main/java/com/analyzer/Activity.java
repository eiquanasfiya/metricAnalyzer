package com.analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Activity {
    private String level;
    private String activityName;
    private Map<String, Object> components;
    private ArrayList<Map<String, Object>> content;

    public Activity() {
        components = new HashMap<>();
        content = new ArrayList<>();
    }

    public String getLevel() {
        return this.level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getActivityName() {
        return this.activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void addComponent(Component component, ArrayList<String> nester) {
        if (nester.isEmpty()) {
            components.put(component.getName(), component);
        } else {
            
            for (String key : nester) {
                if (components.get(nester.get(0)) != null){ // Is key in components
                    Map<String, Object> temp = (HashMap) components.get(key);
                    temp.put(component.getName(), component);
                }
                else {
                    Map<String, Object> temp = new HashMap<>();
                    temp.put(component.getName(), component);
                    components.put(key, temp);
                }
            }
        }
    }

    public Map<String, Object> getComponents() {
        return this.components;
    }

    public void addContent() {
        HashMap finalAcc = new HashMap<>();
        for (String key : components.keySet()){
            if (components.get(key) instanceof Component){
                try {
                    Component comp = (Component) components.get(key);
                    finalAcc.put(key, comp.clone());
                } catch (CloneNotSupportedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else if (components.get(key) instanceof HashMap){
                HashMap temp = new HashMap<>();
                HashMap fin = (HashMap) components.get(key);
                for (Object k : fin.keySet()){
                    Component c = (Component) fin.get(k);
                    try {
                        temp.put(k, c.clone());
                    } catch (CloneNotSupportedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                }
                finalAcc.put(key, temp);
            }
            else {
                // TODO Raise an exception
                System.out.println("This instance should not be here!");
            }
        }
        content.add(finalAcc);
    }

    public ArrayList<Map<String, Object>> getContent() {
        return this.content;
    }
}
